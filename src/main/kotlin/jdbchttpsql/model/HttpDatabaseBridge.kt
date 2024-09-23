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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HttpDatabaseBridge(private val sqlQueries: SQLQueries, private val mongoDBRequests: MongoDBRequests) {
    private val repeatCount = 3
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }
    private val apiEndpoint = "https://api.laut.fm/station/radiotfsc/current_song"

    private suspend fun fetchSongData(): SongData? {
        return try {
            val response: HttpResponse = client.get(apiEndpoint)
            response.body()
        } catch (e: Exception) {
            println("Failed to fetch data: ${e.message}")
            null
        }
    }

    suspend fun processSongData() {
        // Ensure MongoDB collection exists
        mongoDBRequests.ensureCollectionExists()

        repeat(repeatCount) {
            try {
                val songData = fetchSongData()
                if (songData != null) {
                    println("Fetched data: $songData")
                    withContext(Dispatchers.IO) {
                        sqlQueries.insertSongData(songData)
                        mongoDBRequests.insertSongData(songData)
                    }
                } else {
                    println("No data to insert into databases.")
                }
                delay(1000) // Throttle requests
            } catch (e: Exception) {
                println("An error occurred: ${e.message}")
            }
        }

        mongoDBRequests.close()
    }

    fun close() {
        client.close()
    }
}
