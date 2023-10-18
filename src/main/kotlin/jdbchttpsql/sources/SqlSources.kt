package jdbchttpsql.sources

class SqlSources {

    private val url: String = "jdbc:mysql://localhost:3306/sleradio"
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