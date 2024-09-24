package jdbchttpsql.data

import jdbchttpsql.adapters.UserInfo
import kotlinx.serialization.Serializable

/**
 * Data class representing MongoDB connection parameters.
 *
 * This class implements the [ConnectionData] interface and provides default values specific to
 * a MongoDB connection, including default values for the MongoDB URL driver, IP address, and database name.
 *
 * @property urlDriver The URL driver for the MongoDB connection.
 * @property ipAddress The IP address and port for the MongoDB server.
 * @property targetDatabase The target MongoDB database name.
 * @property driverClassName The driver class name, which is an empty string by default.
 * @property userName The username for the MongoDB connection, using default values from [UserInfo].
 * @property password The password for the MongoDB connection, using default values from [UserInfo].
 */
@Serializable
data class MongoDBConnectionData(
    override val urlDriver: String = "mongodb://",
    override val ipAddress: String = "192.168.1.185:27017",
    override val targetDatabase: String = "JBTestMongoDB",
    override val driverClassName: String = "",
    override val userName: String = UserInfo().userName,
    override var password: String = UserInfo().password
) : ConnectionData
