package jdbchttpsql.sources

import jdbchttpsql.UserInfo

class DatabaseSourceBuilder {

    private val urlDriver: String = "jdbc:mysql://"
    private val ipAddress: String = "192.168.1.185:3306"
    private val targetDatabase: String = "JBTestQL"
    private val url: String = "$urlDriver$ipAddress/$targetDatabase"
    private val driver: String = "com.mysql.cj.jdbc.Driver"
    private val user: String = "jeremiah"
    private var password: String = UserInfo().password

    fun getUrl(): String {
        return url
    }

    fun getDriver(): String {
        return driver
    }

    fun getUser(): String {
        return user
    }

    fun getPassword(): String {
        return password
    }
}
