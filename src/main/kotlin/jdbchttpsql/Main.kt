package jdbchttpsql

import jdbchttpsql.adapters.MongoDBDatabaseConnector
import jdbchttpsql.adapters.SQLDatabaseConnector
import jdbchttpsql.data.MongoDBConnectionData
import jdbchttpsql.data.SQLConnectionData
import jdbchttpsql.repository.SQLQueries
import jdbchttpsql.repository.MongoDBRequests
import jdbchttpsql.model.HttpDatabaseBridge
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.ZoneId

/**
 * The main entry point for the application, executed within a coroutine scope enabled by runBlocking.
 *
 * This method initializes the necessary database connectors, loggers, SQL queries, and periodically
 * processes song data fetched from an external HTTP source and stores it in databases, re-checking the
 * target time to determine the delay between fetches.
 *
 * The process involves:
 * - Connecting to SQL and MongoDB databases.
 * - Creating necessary SQL tables if they do not exist.
 * - Setting and updating target times for data fetching cycles.
 * - Fetching and processing song data from an external HTTP source.
 * - Handling potential exceptions and implementing a retry mechanism.
 *
 * @receiver Array<String> Command-line arguments for*/
//TODO: Args - command line arguments for one time configuration.
fun Array<String>.main() =
    runBlocking {

    val sqlConnector = SQLDatabaseConnector(SQLConnectionData())
    val mongoDBConnector = MongoDBDatabaseConnector(MongoDBConnectionData())
    val logger = LoggerFactory.getLogger("Main")
    val sqlQueries = SQLQueries(sqlConnector.connectToDatabase()) // Initialize SQLQueries
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")
    sqlQueries.createTables() // Create tables if they do not exist

    lateinit var mongoDBRequests: MongoDBRequests

    /** TODO: finish moving time related handling to [jdbchttpsql.adapters.TimeHandler] class */
    val targetTimeString = "2024-09-23 23:35:35 +0200"

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

                mongoDBRequests = MongoDBRequests(mongoDBConnector) // Initialize MongoDBRequests/refresh connection
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

                // Wait before the next cycle (you can adjust this delay as needed)
                logger.info("Waiting for 1 second before the next cycle...")
                delay(1000) // Delay for 1 second
            }
        } catch (e: Exception) {
            logger.error("An error occurred during the process: ${e.message}", e)

            // Retry logic in case of an exception
            val currentTime = ZonedDateTime.now(ZoneId.systemDefault())
            val secondsUntilTarget = ChronoUnit.SECONDS.between(currentTime, targetTime)

            if (secondsUntilTarget > 0) {
                delay(secondsUntilTarget * 1000) // Retry after calculated delay
                logger.info("Retrying after $secondsUntilTarget second(s).")
            } else {
                delay(5000) // Default retry delay if target time has passed
                logger.info("Retrying after 5 second")
            }
        }
    }
}