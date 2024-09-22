package jdbchttpsql

import jdbchttpsql.data.DatabaseConnector
import jdbchttpsql.data.FormatData
import jdbchttpsql.data.SocialMediaBuilder
import jdbchttpsql.model.HttpSqlBridge
import jdbchttpsql.repository.MongoDBRequests
import org.ktorm.database.Database
import java.sql.SQLException

// Main function
fun Array<String>.main() {
    // Initialize the database connection
    val database = DatabaseConnector.databaseConnection
    createTables(database)  // Create the SQL table

    // Initialize MongoDB requests
    val mongoDBRequests = MongoDBRequests()
    mongoDBRequests.createCollection()  // Create the MongoDB collection

    // Example of inserting metadata into MongoDB
    val sampleMetaData = FormatData.MetaData(
        album = "Sample Album",
        sku = "SKU123",
        thumb = "http://example.com/thumb.jpg",
        artist = "Sample Artist",
        title = "Sample Title",
        played_show = "Sample Show",
        buy_urls = "http://example.com/buy",
        info_urls = "http://example.com/info",
        duration = "3:30",
        guid = "GUID123",
        timestamp = "2023-09-21T12:00:00Z"
    )

    mongoDBRequests.insertMetaData(sampleMetaData) // Insert sample metadata

    val socialMediaPost = SocialMediaBuilder.Builder()
        .connection("Blue Sky")
        .post("randomly generated")
        .build()

    runSLE()

    // Close MongoDB connection
    mongoDBRequests.close()
}

fun createTables(database: Database) {
    database.useConnection { connection ->
        val createTableSQL = StringBuilder("""
            CREATE TABLE IF NOT EXISTS metadata (
                album VARCHAR(255),
                sku VARCHAR(255),
                thumb VARCHAR(255),
                artist VARCHAR(255),
                title VARCHAR(255),
                played_show VARCHAR(255),
                buy_urls VARCHAR(255),
                info_urls VARCHAR(255),
                duration VARCHAR(255),
                guid VARCHAR(255) PRIMARY KEY,
                time_stamp VARCHAR(255)
            );
        """.trimIndent())

        try {
            connection.createStatement().use { statement ->
                statement.execute(createTableSQL.toString())
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to execute SQL: ${e.message}", e)
        } catch (e: SQLException) {
            // Log SQL specific errors
            println("SQL error: ${e.message}")
        } catch (e: Exception) {
            // Log unexpected errors
            println("Unexpected error: ${e.message}")
        }
    }
}

fun runSLE() {
    val httpSqlBridge = HttpSqlBridge()
    while (true) {
        httpSqlBridge.sleRadio()
        // Add logic to break the loop if needed
    }
}
