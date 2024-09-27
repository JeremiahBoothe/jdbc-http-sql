package jdbchttpsql.repository

import com.mongodb.ConnectionString
import com.mongodb.DuplicateKeyException
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.MongoClient
import com.mongodb.kotlin.client.MongoCollection
import com.mongodb.kotlin.client.MongoDatabase
import jdbchttpsql.data.ConnectionData
import jdbchttpsql.data.SongData
import okhttp3.internal.closeQuietly
import org.bson.Document
import org.slf4j.LoggerFactory

/**
 * This class handles operations related to MongoDB such as initializing the client,
 * ensuring the collection exists, inserting data, and closing the client connection.
 *
 * @constructor Creates an instance of MongoDBRequests with the provided connection data.
 * @param mongoDBConnectionData The connection data required to establish a connection to MongoDB.
 */
class MongoDBRequests
internal constructor(private val mongoDBConnectionData: ConnectionData){
    private val logger = LoggerFactory.getLogger("\u001B[34m${this::class}\u001B[0m")
    //private val logger = LoggerFactory.getLogger("MongoDBRequests")
    // MongoDB client and database initialization
    private val settings: MongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(mongoDBConnectionData.urlDriver + mongoDBConnectionData.ipAddress))
        .applyToConnectionPoolSettings { builder ->
            builder.minSize(2) // Minimum number of connections
            builder.maxSize(10) // Maximum number of connections
        }
        .build()

    private val mongoClient: MongoClient = MongoClient.create(settings)
    private var database: MongoDatabase = mongoClient.getDatabase(mongoDBConnectionData.targetDatabase)
    private val collection: MongoCollection<Document> = database.getCollection("metadata")

    /**
     * Ensures that the MongoDB collection exists.
     *
     * This method checks if any documents exist in the MongoDB collection. If no documents
     * are found, it logs that the collection has been created. MongoDB typically creates
     * collections automatically upon the first insert, so this is mainly a verification step.
     *
     * Logs an informational message when the collection is confirmed to exist or is created.
     */
    fun ensureCollectionExists() {
        // MongoDB creates the collection automatically when you perform the first insert
        // But you can verify if needed:
        if (collection.countDocuments() == 0L) {
            logger.info("Collection 'metadata' created.")
        }
    }

    /**
     * Inserts the given song data into the MongoDB collection.
     *
     * @param songData The song data to insert.
     *
     * @throws IllegalArgumentException if the song data ID is null.
     *
     * Logs an info message when the song data is successfully inserted.
     * Logs an error message if an error occurs while inserting the data.
     */
    fun insertSongData(songData: SongData) {
        requireNotNull(songData.id) { "Song ID cannot be null." }

        val document = Document(
            mapOf(
                "_id" to songData.id,
                "title" to songData.title,
                "album" to songData.album,
                "artist" to songData.artist?.name,
                "length" to songData.length,
                "genre" to songData.genre,
                "releaseyear" to songData.releaseyear,
                "created_at" to songData.created_at,
                "started_at" to songData.started_at,
                "ends_at" to songData.ends_at
            )
        )

        try {
            collection.insertOne(document)
            logger.info("SongData inserted successfully into MongoDB.")
        } catch (e: DuplicateKeyException) {
            logger.error("Failed SongData: $songData")
            //logger.error("Song ")
            //logger.error("Error inserting SongData: ${e.message}", e)
        }
    }

    /**
     * Closes the MongoDB client connection.
     *
     * This method attempts to close the connection to the MongoDB client and logs an informational message upon success.
     * If an error occurs during the closure, it logs an error message with the exception details.
     */
    fun close() {
        try {
            this.mongoClient.use { client ->
                client.close()
            }
            logger.info("MongoDB connection closed.")
        } catch (e: Exception) {
            logger.error("Error closing MongoDB client: ${e.message}", e)
        }
    }
}
