package jdbchttpsql

import jdbchttpsql.repository.SQLQueries
import jdbchttpsql.repository.MongoDBRequests
import jdbchttpsql.model.HttpDatabaseBridge
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory

suspend fun main() {
    val logger = LoggerFactory.getLogger("Main")
    val sqlQueries = SQLQueries() // Initialize SQLQueries
    sqlQueries.createTables() // Create tables if they do not exist

    //val mongoDBRequests = MongoDBRequests() // Initialize MongoDBRequests
    //val bridge = HttpDatabaseBridge(sqlQueries, mongoDBRequests)

    while (true) {

        try {
            val mongoDBRequests = MongoDBRequests() // Initialize MongoDBRequests
            val bridge = HttpDatabaseBridge(sqlQueries, mongoDBRequests)
            logger.info("Starting the process to fetch and store song data...")
            bridge.processSongData() // Fetch and store song data
            logger.info("Process completed.")
            // The closing resources code will not be reached in this infinite loop
            // You may want to implement a way to exit the loop gracefully
            logger.info("Closing resources...")
            bridge.close() // Close the Ktor client when done
            mongoDBRequests.close() // Close MongoDB requests
            //sqlQueries.close() // Close SQL queries if applicable
        } catch (e: Exception) {
            logger.error("An error occurred during the process: ${e.message}", e)
            delay(1000) // Wait before retrying
        }

        // Optionally add a delay before the next iteration to prevent rapid looping
        delay(5000) // Delay before the next cycle (5 seconds, adjust as needed)
    }


}
