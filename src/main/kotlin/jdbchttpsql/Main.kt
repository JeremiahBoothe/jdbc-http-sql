package jdbchttpsql

import jdbchttpsql.data.SocialMediaBuilder
import jdbchttpsql.model.HttpSqlBridge


//TODO: Args - command line arguments for one time configuration.
fun main(Args: Array<String>) {


    val socialMediaPost = SocialMediaBuilder.Builder()
        .connection("Blue Sky")
        .post("randomly generated")
        .build()

    fun runSLE() {
        val httpSqlBridge = HttpSqlBridge()
        var x = 1
        while (x != 2) {
            httpSqlBridge.sleRadio()
        }
    }

    val runSLE = runSLE()
    return runSLE
}