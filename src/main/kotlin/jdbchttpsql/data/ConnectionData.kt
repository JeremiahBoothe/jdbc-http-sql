package jdbchttpsql.data

interface ConnectionData {
    val urlDriver: String
    val ipAddress: String
    val targetDatabase: String
    //val connectionUrl: String
    val driverClassName: String
    val username: String
    val password: String
}
