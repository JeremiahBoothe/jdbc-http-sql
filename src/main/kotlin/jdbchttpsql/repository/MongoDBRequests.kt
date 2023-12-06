package jdbchttpsql.repository

import com.google.gson.Gson
import com.mongodb.kotlin.client.MongoClient
import com.mongodb.kotlin.client.MongoCollection
import com.mongodb.kotlin.client.MongoDatabase
import jdbchttpsql.data.FormatDataMongoDB
import org.bson.Document
import java.net.URL

class MongoDBRequests {

    private val webPage = URL("https://www.radiojar.com/api/stations/2z8y97h1v8quv/now_playing/")
    private val gson = Gson()
    private val data = webPage.readText()

    // Connect to MongoDB (adjust connection string accordingly)
    private val mongoClient = MongoClient.create("mongodb://192.168.1.185:27017")
    val database: MongoDatabase = mongoClient.getDatabase("JBTestQL")
    private val collection: MongoCollection<Document> = database.getCollection("TestCollection")

    private val mongoDbFormat: FormatDataMongoDB = FormatDataMongoDB(collection)
    // Convert Gson data to BSON and insert into MongoDB
    val td: Document = Document.parse(gson.toJson(collection))

    //collection.insertOne(td)

    /*
        // If you want to retrieve data from MongoDB
        val cursor = collection.find(yourFilterHere)
        for (document in cursor) {
            // Process each BSON document
            val metaData = gson.fromJson(document.toJson(), FormatData.MetaData::class.java)
            // Do something with metaData
        }
    */

    // Close MongoDB client when done
    //mongoClient.close()
}