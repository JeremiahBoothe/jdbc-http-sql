package jdbchttpsql.model

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import jdbchttpsql.data.SongData
import jdbchttpsql.repository.MongoDBRequests
import jdbchttpsql.repository.SQLQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

/**
 * A bridge connecting HTTP requests to database operations for fetching and
 * storing song data. This class interacts with both SQL and MongoDB databases.
 *
 * @property sqlQueries An instance of `SQLQueries` to perform SQL database operations.
 * @property mongoDBRequests An instance of `MongoDBRequests` to interact with MongoDB.
 */
class HttpDatabaseBridge(private val sqlQueries: SQLQueries, private val mongoDBRequests: MongoDBRequests) {
    private val logger = LoggerFactory.getLogger("HttpDatabaseBridge")
    private val repeatCount = 3
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }
    private val apiEndpoint = "https://api.laut.fm/station/radiotfsc/current_song"

    /**
     * Fetches song data from a remote API endpoint.
     *
     * This function attempts to retrieve song data by sending an HTTP GET request
     * to the specified API endpoint. If the request is successful, the response body
     * is parsed into a `SongData.SongData` object. In case of an exception, the function
     * returns null and logs the error message.
     *
     * @return The fetched `SongData.SongData` object, or null if an error occurs.
     */
    suspend fun fetchSongData(): SongData.SongData? {
        return try {
            val response: HttpResponse = client.get(apiEndpoint)
            response.body()
        } catch (e: Exception) {
            println("Failed to fetch data: ${e.message}")
            null
        }
    }

    /**
     * Processes song data by fetching it from a remote API and inserting it into
     * both SQL and MongoDB databases.
     *
     * This function performs the following steps:
     * - Ensures that the required MongoDB collection and SQL table exist.
     * - Repeatedly fetches song data from a remote API.
     * - If song data is retrieved, it is inserted into both SQL and MongoDB databases.
     * - Logs the successful insertion of data and any errors that occur during the process.
     * - Introduces a delay to throttle the requests.
     *
     * @throws Exception if any errors occur during the processing of song data.
     */
    suspend fun processSongData() {
        // Ensure MongoDB collection exists
        mongoDBRequests.ensureCollectionExists()
        // Ensure SQL table Exists
        sqlQueries.ensureTableExists()
        repeat(repeatCount) {
            try {
                val songData = fetchSongData()
                if (songData != null) {
                    println("Fetched data: $songData")
                    withContext(Dispatchers.IO) {
                        sqlQueries.insertSongData(songData)
                        logger.info("SQL exists: $songData")
                        mongoDBRequests.insertSongData(songData)
                        logger.info("MongoDB Exists: $songData")

                    }
                } else {
                    println("No data to insert into databases.")
                }
                delay(1000) // Throttle requests
            } catch (e: Exception) {
                logger.error("An error occurred: ${e.message}")
            }
        }

        mongoDBRequests.close()
    }

    fun close() {
        client.close()
    }
}
