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

/**
 * A utility class to manage SQL queries for the 'metadata' table.
 * Provides methods to create tables, ensure their existence, and insert song data.
 *
 * @constructor Creates an instance of SQLQueries with a given database connection.
 * @property databaseConnection The database connection instance used for executing the SQL queries.
 */
class SQLQueries
    internal constructor(private val databaseConnection: Database) {
    private val logger = LoggerFactory.getLogger(SQLQueries::class.java)

    /**
     * Represents the database table structure for storing song metadata.
     * The table is named "metadata" and contains various columns for song data.
     */
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

    /**
     * SQL statement to create the "metadata" table if it does not exist.
     *
     * The "metadata" table includes the following columns:
     * - id: Primary key of type INT.
     * - title: Song title of type VARCHAR(255).
     * - album: Album name of type VARCHAR(255).
     * - artist: Artist name of type VARCHAR(255).
     * - length: Song length in seconds of type INT.
     * - genre: Music genre of type VARCHAR(255).
     * - releaseyear: Year of release of type INT.
     * - created_at: Timestamp of creation of type TEXT.
     * - started_at: Timestamp when the song started playing of type TEXT.
     * - ends_at: Timestamp when the song ended playing of type TEXT.
     */
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

    /**
     * Creates necessary tables in the database if they do not already exist.
     * Connects to the database, executes the SQL statement to create tables, and logs the operation status.
     * Logs SQL and unexpected errors if any occur during the table creation process.
     *
     * @throws SQLException If an error occurs while executing the SQL statement.
     * @throws Exception If an unexpected error occurs during the database operations.
     */
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

    /**
     * Ensures that the 'metadata' table exists in the database.
     *
     * Checks if the 'metadata' table is present by querying the `information_schema.tables`.
     * If the table does not exist, it invokes the `createTables` method to create the necessary tables.
     * Logs actions and errors during the process.
     *
     * This method is designed to be called before performing operations that depend on the existence of the 'metadata' table.
     *
     * @throws Exception If an error occurs during the database check or table creation.
     */
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

    /**
     * Inserts a new song record into the database.
     *
     * This method takes a `SongData` object and inserts its details into the `SongTable` in the database.
     * The operation is performed within a transaction to ensure data integrity. If the insertion is
     * successful, a confirmation message is logged. Any SQL or general exceptions encountered during
     * the process are caught and logged.
     *
     * @param songData The song data to be inserted into the database.
     * @throws IllegalArgumentException If the song ID is null.
     * @throws SQLException If an error occurs during the SQL execution.
     * @throws Exception If an unexpected error occurs.
     */
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
            //logger.error("Failed SongData: $songData")
        } catch (e: Exception) {
            logger.error("Error inserting SongData into SQL: ${e.message}", e)
            //logger.error("Failed SongData: $songData")
        }
    }
}
