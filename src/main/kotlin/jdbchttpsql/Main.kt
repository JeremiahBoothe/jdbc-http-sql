package jdbchttpsql

import jdbchttpsql.adapters.MongoDBDatabaseConnector
import jdbchttpsql.adapters.SQLDatabaseConnector
import jdbchttpsql.data.MongoDBConnectionData
import jdbchttpsql.data.SQLConnectionData
import jdbchttpsql.repository.SQLQueries
import jdbchttpsql.repository.MongoDBRequests
import jdbchttpsql.model.HttpDatabaseBridge
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.ZoneId

/**
 * Entry point for the application. This suspend function initializes database connectors, sets up logging,
 * and enters an infinite loop to periodically fetch and process song data from an API.
 *
 * The function performs the following tasks:
 * - Initializes SQL and MongoDB database connectors.
 * - Sets up SQL queries and creates tables if they do not exist.
 * - Sets an initial target time for synchronization.
 * - Enters a loop where it:
 *     - Waits for the calculated interval until the next target time.
 *     - Fetches song data and processes it.
 *     - Updates the target time based on the fetched song length.
 * - Handles errors by logging them and retrying after specified delays.
 *
 * The function uses coroutines for suspending execution and delays.
 *
 * @throws Exception If an error occurs during the process.
 */
suspend fun main() {
    val sqlConnector = SQLDatabaseConnector(SQLConnectionData())
    val mongoDBConnector = MongoDBDatabaseConnector(MongoDBConnectionData())

    val logger = LoggerFactory.getLogger("Main")
    val sqlQueries = SQLQueries(sqlConnector.connectToDatabase()) // Initialize SQLQueries
    sqlQueries.createTables() // Create tables if they do not exist

    var mongoDBRequests = MongoDBRequests(mongoDBConnector)
    // Initial target time; consider updating this based on the actual data from your API
    val targetTimeString = "2024-09-23 23:35:35 +0200"
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")
    var targetTime = ZonedDateTime.parse(targetTimeString, formatter)

    while (true) {
        try {
            // Dynamically get current time in each iteration
            val currentTime = ZonedDateTime.now(ZoneId.systemDefault())
            val secondsUntilTarget = ChronoUnit.SECONDS.between(currentTime, targetTime)

            // Check if target time is in the future
            if (secondsUntilTarget > 0) {
                logger.info("Waiting for $secondsUntilTarget seconds until the target time...")
                delay(secondsUntilTarget * 1000) // Delay in milliseconds
                logger.info("Delaying for ${secondsUntilTarget * 1000} ms")
            } else {
                logger.info("Target time reached or passed, starting the process...")

                mongoDBRequests = MongoDBRequests(mongoDBConnector) // Initialize MongoDBRequests
                val bridge = HttpDatabaseBridge(sqlQueries, mongoDBRequests)
                // Fetch song data and get its length
                val songLengthInSeconds = bridge.fetchSongData()?.length ?: 30 // Default to 30 if null
                logger.info("Fetched song length: $songLengthInSeconds seconds")

                // Update target time to the current time plus the song length
                targetTime = currentTime.plusSeconds(songLengthInSeconds.toLong())
                logger.info("New target time set to: $targetTime")

                logger.info("Starting the process to fetch and store song data...")
                bridge.processSongData() // Fetch and store song data

                logger.info("Process completed.")
                logger.info("Closing resources...")
                bridge.close() // Close the Ktor client when done
                //mongoDBRequests.close() // Close MongoDB requests

                // Wait before the next cycle (you can adjust this delay as needed)
                logger.info("Waiting for 1 second before the next cycle...")
                delay(1000) // Delay for 5 seconds
            }
        } catch (e: Exception) {
            logger.error("An error occurred during the process: ${e.message}", e)

            // Retry logic in case of an exception
            val currentTime = ZonedDateTime.now(ZoneId.systemDefault())
            val secondsUntilTarget = ChronoUnit.SECONDS.between(currentTime, targetTime)

            if (secondsUntilTarget > 0) {
                delay(secondsUntilTarget * 1000) // Retry after calculated delay
                logger.info("Retrying after ${secondsUntilTarget * 1000} ms")
            } else {
                delay(5000) // Default retry delay if target time has passed
                logger.info("Retrying after 5000 ms")
            }
        }
    }
}