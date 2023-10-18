package jdbchttpsql.data

import jdbchttpsql.sources.SqlSources
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
object DatabaseConnector{

    private val source: String = SqlSources().getUrl()
    private val driver: String = SqlSources().getDriver()
    private val user: String = SqlSources().getUser()
    private val password: String = SqlSources().getPassword()

    val databaseConnection = Database.connect(
        url = source,
        driver = driver,
        user = user,
        password = password,
        logger = ConsoleLogger(threshold = LogLevel.INFO),
    )
}