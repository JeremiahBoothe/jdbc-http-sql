package jdbchttpsql.data

import jdbchttpsql.UserInfo
import kotlinx.serialization.Serializable

/**
 * @property urlDriver
 */
@Serializable
data class RadioTfscConnectionData(
    override val urlDriver: String,
    override val ipAddress: String,
    override val targetDatabase: String,
    //override val connectionUrl: String,
    override val driverClassName: String,
    override val username: String,
    override var password: String
) : ConnectionData
