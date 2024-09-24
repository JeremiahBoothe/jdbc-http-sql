package jdbchttpsql.data

/**
 * Interface representing the essential data required for database connections.
 *
 * Implement this interface to provide connection parameters for different types of databases,
 * such as MongoDB or SQL databases. Various classes that require database connection information
 * can utilize implementations of this interface to access the necessary connection details.
 *
 * @property urlDriver The URL driver string used to initiate the connection.
 * @property ipAddress The IP address and port number of the database server.
 * @property targetDatabase The name of the target database to connect to.
 * @property driverClassName The class name of the JDBC or other relevant driver for the connection.
 * @property userName The username used for authentication with the database.
 * @property password The password used for authentication with the database.
 */
interface ConnectionData {
    val urlDriver: String
    val ipAddress: String
    val targetDatabase: String
    //val connectionUrl: String
    val driverClassName: String
    val userName: String
    val password: String
}
