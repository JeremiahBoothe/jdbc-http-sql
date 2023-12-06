package jdbchttpsql

import jdbchttpsql.model.HttpSqlBridge
import kotlin.test.Test

class HttpSqlBridgeTest {
    val SLERADIO = HttpSqlBridge()

    @Test
    fun testSLERadio() {
        SLERADIO.sleRadio().toString()
        println(SLERADIO.sleRadio().toString())
    }

}