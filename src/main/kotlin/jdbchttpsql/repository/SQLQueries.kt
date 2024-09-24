package jdbchttpsql.repository

import jdbchttpsql.data.SongData
import org.ktorm.database.Database
import org.ktorm.dsl.insert
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.slf4j.LoggerFactory
import java.sql.SQLException
import java.sql.ResultSet
import java.sql.Statement

class SQLQueries
    internal constructor(private val databaseConnection: Database) {
    private val logger = LoggerFactory.getLogger(SQLQueries::class.java)

    // Define the SongTable corresponding to your SQL table schema
    object SongTable : Table<Nothing>("metadata") {
        val id = int("id").primaryKey()
        val title = text("title")
        val album = text("album")
        val artist = text("artist")
        val length = int("length")
        val genre = text("genre")
        val releaseyear = int("releaseyear")
        val createdAt = text("created_at")
        val startedAt = text("started_at")
        val endsAt = text("ends_at")
    }

    // SQL statement for creating the table
    private val createTableSQL = """
        CREATE TABLE IF NOT EXISTS metadata (
            id INT PRIMARY KEY,
            title VARCHAR(255),
            album VARCHAR(255),
            artist VARCHAR(255),
            length INT,
            genre VARCHAR(255),
            releaseyear INT,
            created_at TEXT,
            started_at TEXT,
            ends_at TEXT
        );
    """.trimIndent()

    // Create the tables if they do not exist
    fun createTables() {
        databaseConnection.useConnection { connection ->
            try {
                connection.createStatement().use { statement ->
                    statement.execute(createTableSQL)
                }
                logger.info("Table 'metadata' ensured to exist.")
            } catch (e: SQLException) {
                logger.error("SQL error during table creation: ${e.message}", e)
            } catch (e: Exception) {
                logger.error("Unexpected error during table creation: ${e.message}", e)
            }
        }
    }

    // Check if the table exists and log the result
    suspend fun ensureTableExists() {
        val tableName = "metadata"
        val sqlCheckTableExists = "SELECT * FROM information_schema.tables WHERE table_name = '$tableName';"

        try {
            databaseConnection.useConnection { conn ->
                val statement: Statement = conn.createStatement()
                val resultSet: ResultSet = statement.executeQuery(sqlCheckTableExists)

                if (!resultSet.next()) {
                    // The table does not exist, create it
                    logger.info("Table '$tableName' does not exist, creating it.")
                    createTables()
                } else {
                    logger.info("Table '$tableName' already exists.")
                }
            }
        } catch (e: Exception) {
            logger.error("Error ensuring table exists: ${e.message}", e)
        }
    }

    // Insert SongData directly into SQL
    fun insertSongData(songData: SongData.SongData) {
        try {
            // Validate songData before insertion
            requireNotNull(songData.id) { "Song ID cannot be null." }

            databaseConnection.useTransaction {
                logger.info("Starting transaction for inserting song data.")
                databaseConnection.insert(SongTable) {
                    set(SongTable.id, songData.id)
                    set(SongTable.title, songData.title)
                    set(SongTable.album, songData.album)
                    set(SongTable.artist, songData.artist?.name)
                    set(SongTable.length, songData.length)
                    set(SongTable.genre, songData.genre ?: "") // Handle nullable genre
                    set(SongTable.releaseyear, songData.releaseyear)
                    set(SongTable.createdAt, songData.created_at)
                    set(SongTable.startedAt, songData.started_at)
                    set(SongTable.endsAt, songData.ends_at)
                }
                logger.info("SongData inserted successfully into SQL.")
                println("Entry Added SQL!")
            }
        } catch (e: SQLException) {
            logger.error("SQL error inserting SongData into SQL: ${e.message}", e)
            logger.error("Failed SongData: $songData")
        } catch (e: Exception) {
            logger.error("Error inserting SongData into SQL: ${e.message}", e)
            logger.error("Failed SongData: $songData")
        }
    }
}
