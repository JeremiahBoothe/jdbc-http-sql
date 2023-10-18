package jdbchttpsql.model

import jdbchttpsql.repository.HttpRequests
import jdbchttpsql.repository.InsertMethods
import jdbchttpsql.repository.SQLQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HttpSqlBridge {
    fun sleRadio() {
        val insertMethods = InsertMethods()
        val httpRequests = HttpRequests()
        val databaseConnector = SQLQueries().database
        /** Connect to database, same instance as SQLQueries()
      takes Json data from http and implements it in sql entry*/
        var recursionVariable = 1

        println(httpRequests)
        println(databaseConnector)

        /*Wrapped in runBlocking coroutine just because
        the launch(Dispatchers.IO) was because it puts it in the IO thread, best for http/sql requests, etc...*/
        runBlocking {
            launch(Dispatchers.IO) {
                fun insertStuff() {
                    insertMethods.insertSLERadio()
                }; while (recursionVariable != 3) {
                try {
                    delay(1000)
                    insertStuff()
                } catch (e: StackOverflowError) {
                    return@launch
                } finally {
                    return@launch
                }
            }
                insertStuff()
                return@launch
            }
        }
    }
    fun runSLE() {
        val httpSqlBridge = HttpSqlBridge()
        var x = 1
        while (x != 2) {
            httpSqlBridge.sleRadio()
        }
    }
        //database.deleteAll(FormatData.RadioMetaData)
}


