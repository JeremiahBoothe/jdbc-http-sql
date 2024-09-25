package jdbchttpsql.data

import jdbchttpsql.adapters.UserInfo
import jdbchttpsql.adapters.loadUserInfoFromFile
import kotlinx.serialization.Serializable

/**
 * Data class representing the connection parameters required to establish a connection with a MongoDB database.
 *
 * This class implements the [ConnectionData] interface and provides specific default values for MongoDB.
 *
 * @property urlDriver The URL driver string used to initiate the MongoDB connection. Defaults to "mongodb://".
 * @property ipAddress The IP address and port number of the MongoDB server. Defaults to "192.168.1.185:27017".
 * @property targetDatabase The name of the target MongoDB database to connect to. Defaults to "JBTestMongoDB".
 * @property driverClassName The class name of the JDBC or other relevant driver for the MongoDB connection. Defaults to an empty string.
 * @property userName The username used for authentication with the MongoDB database, loaded from a credentials file.
 * @property password The password used for authentication with the MongoDB database, loaded from a credentials file.
 */
@Serializable
data class MongoDBConnectionData(
    override val urlDriver: String = "mongodb://",
    override val ipAddress: String = "192.168.1.185:27017",
    override val targetDatabase: String = "JBTestMongoDB",
    override val driverClassName: String = "",
    override val userName: String = loadUserInfoFromFile().userName,
    override var password: String = loadUserInfoFromFile().password
) : ConnectionData
