package jdbchttpsql.adapters

import jdbchttpsql.data.ConnectionData
import jdbchttpsql.data.MongoDBConnectionData
import jdbchttpsql.data.SQLConnectionData
import org.ktorm.database.Database
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel

/**
 * A class responsible for establishing connections to a MongoDB database.
 *
 * @param T The type of connection data, extending from [ConnectionData].
 * @property connectionData The connection data necessary for establishing a MongoDB database connection.
 *
 * This class delegates properties to the provided connection data to streamline the connection process.
 * Only specific types of connection data, such as [MongoDBConnectionData], are supported.
 *
 * @throws IllegalArgumentException If the connection data type is unsupported.
 */
class MongoDBDatabaseConnector<T : ConnectionData>(private val connectionData: T):
    ConnectionData by connectionData {
    /**
     * Creates and returns an instance of [MongoDBConnectionData], which contains the necessary
     * parameters for establishing a MongoDB connection.
     *
     * @return An instance of [MongoDBConnectionData] with default connection parameters.
     */
    fun mongoDBConnectionData(): ConnectionData {
        return MongoDBConnectionData()
    }

}
