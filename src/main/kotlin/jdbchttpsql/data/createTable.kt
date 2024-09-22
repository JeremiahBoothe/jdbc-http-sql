package jdbchttpsql.data

import org.ktorm.database.Database
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Users : Table<Nothing>("users") {
    val id = int("id").primaryKey().bindTo { null }
    val username = varchar("username").bindTo { null }
    val password = varchar("password").bindTo { null }
    val email = varchar("email").bindTo { null }
}

fun createTable(database: Database) {
    database.useConnection { connection ->
        connection.createStatement().execute(
            """
            CREATE TABLE IF NOT EXISTS users (
                id VARCHAR(36) PRIMARY KEY,
                username VARCHAR(255),
                password VARCHAR(255),
                email VARCHAR(255)
            )
            """.trimIndent()
        )
    }
}

