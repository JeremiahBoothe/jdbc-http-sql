package jdbchttpsql

import jdbchttpsql.model.HttpDatabaseBridge
import kotlin.test.Test

class HttpDatabaseBridgeTest {
    val SLERADIO = HttpDatabaseBridge()

    @Test
    fun testSLERadio() {
        SLERADIO.sleRadio().toString()
        println(SLERADIO.sleRadio().toString())
    }

}