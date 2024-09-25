package jdbchttpsql.adapters

import jdbchttpsql.data.ConnectionData
import jdbchttpsql.data.SQLConnectionData
import org.ktorm.database.Database
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel

/**
 * A class that facilitates connecting to a SQL database using the provided connection data.
 *
 * @param T The type of connection data, extending from [ConnectionData].
 * @property connectionData The connection data necessary for establishing a SQL database connection.
 *
 * This class delegates properties to the provided connection data to streamline the connection process.
 * Only specific types of connection data, such as [SQLConnectionData], are supported.
 *
 * @throws IllegalArgumentException If the connection data type is unsupported.
 */
class SQLDatabaseConnector<T : ConnectionData>(private val connectionData: T):
    ConnectionData by connectionData {
    /**
     *
     */
    fun connectToDatabase(): Database {
        val driverClass = when (connectionData) {
            is SQLConnectionData -> connectionData.driverClassName
            else -> throw IllegalArgumentException("Unsupported connection data type")
        }
        return Database.connect(
            url = "$urlDriver$ipAddress/$targetDatabase",  // Delegated properties
            driver = driverClass,
            user = userName,
            password = password,
            logger = ConsoleLogger(threshold = LogLevel.INFO)
        )
    }
}
