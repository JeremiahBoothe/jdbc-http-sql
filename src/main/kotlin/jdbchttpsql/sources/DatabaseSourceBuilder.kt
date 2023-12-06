package jdbchttpsql.sources

class DatabaseSourceBuilder {

    private val urlDriver: String = "jdbc:mysql://"
    //private val url: String = "jdbc:mysql://localhost:3306/sleradio"
    private val ipAddress: String = "192.168.1.185"
    private val targetDatabase: String = "/ktorm"
    private val url: String = urlDriver + ipAddress + targetDatabase
    private val driver: String = "com.mysql.cj.jdbc.Driver"
    private val user: String = "JBTestQL"
    private val password: String = "test"

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