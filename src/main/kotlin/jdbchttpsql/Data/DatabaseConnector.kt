package jdbchttpsql.Data

import org.ktorm.database.Database
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel

object DatabaseConnector {

    val databaseConnection = Database.connect(
        "jdbc:mysql://localhost:3306/sleradio",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "JBTestQL",
        password = "test",
        logger = ConsoleLogger(threshold = LogLevel.INFO),
    )
}