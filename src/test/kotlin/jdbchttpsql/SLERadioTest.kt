package jdbchttpsql

import jdbchttpsql.Model.SLERadio
import org.junit.jupiter.api.Test

class SLERadioTest {
    val SLERADIO = SLERadio()

    @Test
    fun testSLERadio() {
        SLERADIO.sleRadio().toString()
        println("${SLERADIO.sleRadio().toString()}")
    }

}