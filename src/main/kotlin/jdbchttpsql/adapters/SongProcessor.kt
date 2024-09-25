package jdbchttpsql.adapters

import jdbchttpsql.model.HttpDatabaseBridge
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * A class that processes song data by interacting with both an HTTP database bridge and a logger.
 * This class fetches song data, updates target times, and logs the progress.
 *
 * @property bridge An instance of `HttpDatabaseBridge` to handle database interactions.
 * @property logger An instance of `org.slf4j.Logger` to log messages and errors.
 */
class SongProcessor(private val bridge: HttpDatabaseBridge, private val logger: org.slf4j.Logger) {
    suspend fun processSongData(targetTime: ZonedDateTime, timeHandler: TimeHandler): ZonedDateTime {
        // Fetch song data and get its length
        val songLengthInSeconds = bridge.fetchSongData()?.length ?: 30 // Default to 30 if null
        logger.info("Fetched song length: $songLengthInSeconds seconds")

        // Update target time to the current time plus the song length
        val newTargetTime = timeHandler.updateTargetTime(ZonedDateTime.now(ZoneId.systemDefault()), songLengthInSeconds.toLong())
        logger.info("New target time set to: $newTargetTime")

        logger.info("Starting the process to fetch and store song data...")
        bridge.processSongData() // Fetch and store song data

        return newTargetTime
    }
}