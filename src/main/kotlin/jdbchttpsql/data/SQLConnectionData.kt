package jdbchttpsql.data

import jdbchttpsql.adapters.loadUserInfoFromFile
import kotlinx.serialization.Serializable

/**
 * Data class representing the essential data required for establishing an SQL database connection.
 *
 * This class provides the necessary parameters to connect to an SQL database, including the URL driver,
 * IP address, target database, driver class name, username, and password. It implements the [ConnectionData]
 * interface and acts as a concrete implementation specifically for SQL databases.
 *
 * @property urlDriver The JDBC URL prefix used to establish the connection. Defaults to "jdbc:mysql://".
 * @property ipAddress The IP address and port number of the database server. Defaults to "192.168.1.185:3306".
 * @property targetDatabase The name of the database to connect to. Defaults to "JBTestQL".
 * @property driverClassName The fully qualified name of the JDBC driver class. Defaults to "com.mysql.cj.jdbc.Driver".
 * @property userName The username for authenticating the connection, loaded from a credentials file.
 * @property password The password for authenticating the connection, loaded from a credentials file.
 */
@Serializable
data class SQLConnectionData(
    override val urlDriver: String = "jdbc:mysql://",
    override val ipAddress: String = "192.168.1.185:3306",
    override val targetDatabase: String = "JBTestQL",
    override val driverClassName: String = "com.mysql.cj.jdbc.Driver",
    override val userName: String = loadUserInfoFromFile().userName,
    override val password: String = loadUserInfoFromFile().password
): ConnectionData

