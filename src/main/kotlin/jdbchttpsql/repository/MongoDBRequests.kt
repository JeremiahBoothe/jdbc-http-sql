package jdbchttpsql.repository

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.MongoClient
import com.mongodb.kotlin.client.MongoCollection
import com.mongodb.kotlin.client.MongoDatabase
import jdbchttpsql.data.SongData
import org.bson.Document
import org.slf4j.LoggerFactory

class MongoDBRequests {
    private val logger = LoggerFactory.getLogger(MongoDBRequests::class.java)

    // MongoDB client and database initialization
    private val settings: MongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString("mongodb://192.168.1.185:27017"))
        .applyToConnectionPoolSettings { builder ->
            builder.minSize(2) // Minimum number of connections
            builder.maxSize(10) // Maximum number of connections
        }
        .build()

    private val mongoClient: MongoClient = MongoClient.create(settings)
    //private val mongoClient: MongoClient = MongoClient.create("mongodb://192.168.1.185:27017")
    private var database: MongoDatabase = mongoClient.getDatabase("JBTestQL")
    private val collection: MongoCollection<Document> = database.getCollection("metadata")

    // Ensure collection exists (optional, as MongoDB creates it on first insert)
    fun ensureCollectionExists() {
        // MongoDB creates the collection automatically when you perform the first insert
        // But you can verify if needed:
        if (collection.countDocuments() == 0L) {
            println("Collection 'metadata' created.")
        }
    }
    // Insert SongData directly into MongoDB
    fun insertSongData(songData: SongData.SongData) {
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
            println("Entry Added MongoDB!")
            logger.info("SongData inserted successfully into MongoDB.")
        } catch (e: Exception) {
            logger.error("Error inserting SongData: ${e.message}", e)
            logger.error("Failed SongData: $songData")
        }
    }

    // Close MongoDB client
    fun close() {
        try {
            mongoClient.close()
            logger.info("MongoDB connection closed.")
        } catch (e: Exception) {
            logger.error("Error closing MongoDB client: ${e.message}", e)
        }
    }
}
