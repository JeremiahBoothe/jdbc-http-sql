package jdbchttpsql.repository

import com.google.gson.Gson
import com.mongodb.kotlin.client.MongoClient
import com.mongodb.kotlin.client.MongoCollection
import com.mongodb.kotlin.client.MongoDatabase
import jdbchttpsql.data.FormatData
import org.bson.Document

class MongoDBRequests {

    // Connect to MongoDB (adjust connection string accordingly)
    private val mongoClient = MongoClient.create("mongodb://192.168.1.185:27017")
    val database: MongoDatabase = mongoClient.getDatabase("JBTestQL")
    private val collection: MongoCollection<Document> = database.getCollection("TestCollection")

    private val gson = Gson()

    // Create the MongoDB collection based on the data class schema
    fun createCollection() {
        // Here you can implement logic to ensure the collection is created
        // or modify the existing one based on the schema if necessary.
    }

    // Insert metadata into MongoDB
    fun insertMetaData(metaData: FormatData.MetaData) {
        val document = Document(mapOf(
            "album" to metaData.album,
            "sku" to metaData.sku,
            "thumb" to metaData.thumb,
            "artist" to metaData.artist,
            "title" to metaData.title,
            "played_show" to metaData.played_show,
            "buy_urls" to metaData.buy_urls,
            "info_urls" to metaData.info_urls,
            "duration" to metaData.duration,
            "guid" to metaData.guid,
            "time_stamp" to metaData.timestamp
        ))
        collection.insertOne(document)
    }

    // Close MongoDB client when done
    fun close() {
        mongoClient.close()
    }
}
