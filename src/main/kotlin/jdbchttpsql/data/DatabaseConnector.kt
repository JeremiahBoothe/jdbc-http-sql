package jdbchttpsql.data

import jdbchttpsql.sources.DatabaseSourceBuilder
import org.ktorm.database.Database
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel

/** Gets the database connection arguments and initializes the database connection as Singleton Object.
 * @author Jeremiah Boothe
 * @property [source] Url Source of database connection.
 * @property [driver] Driver type for database connection (ie MySQL).
 * @property [user] User Name for database login.
 * @property [password] User Password for database login.
 */
/** Initializes the database connection using generic connection data.
 * @param [sourceValues] The connection data needed for database initialization.
 */
class DatabaseConnector<T : ConnectionData>(private val connectionData: T) {

    fun connectToDatabase(): Database {
        return Database.connect(
            url = "${connectionData.urlDriver}${connectionData.ipAddress}/${connectionData.targetDatabase}",
            driver = (connectionData as? SleConnectionData)?.driverClassName ?: "",
            user = connectionData.username,
            password = connectionData.password,
            logger = ConsoleLogger(threshold = LogLevel.INFO)
        )
    }
}