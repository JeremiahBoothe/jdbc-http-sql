package jdbchttpsql.data

import jdbchttpsql.UserInfo
import kotlinx.serialization.Serializable
import org.ktorm.schema.BaseTable

/**
 * @property urlDriver
 */
@Serializable
data class SleConnectionData(
    private val urlDriver: String = "jdbc:mysql://",
    private val ipAddress: String = "192.168.1.185:3306",
    private val targetDatabase: String = "JBTestQL",
    private val url: String = "$urlDriver$ipAddress/$targetDatabase",
    private val driver: String = "com.mysql.cj.jdbc.Driver",
    private val user: String = "jeremiah",
    private var password: String = UserInfo().password
)
