package jdbchttpsql.data

import jdbchttpsql.adapters.UserInfo
import kotlinx.serialization.Serializable

/**
 * Data class representing SQL connection parameters.
 *
 * This class implements the [ConnectionData] interface and provides default values specific to
 * an SQL connection, including the URL driver, IP address, target database, and driver class name.
 *
 * @property urlDriver The URL driver for the SQL connection, defaulting to "jdbc:mysql://".
 * @property ipAddress The IP address and port for the SQL server, defaulting to "192.168.1.185:3306".
 * @property targetDatabase The target database name, defaulting to "JBTestQL".
 * @property driverClassName The class name of the SQL driver, defaulting to "com.mysql.cj.jdbc.Driver".
 * @property userName The username for the SQL connection, using default values from [UserInfo].
 * @property password The password for the SQL connection, using default values from [UserInfo].
 */
@Serializable
data class SQLConnectionData(
    override val urlDriver: String = "jdbc:mysql://",
    override val ipAddress: String = "192.168.1.185:3306",
    override val targetDatabase: String = "JBTestQL",
    override val driverClassName: String = "com.mysql.cj.jdbc.Driver",
    override val userName: String = UserInfo().userName,
    override val password: String = UserInfo().password
): ConnectionData
