package jdbchttpsql

import jdbchttpsql.data.DatabaseConnectionBuilder
import jdbchttpsql.data.SocialMediaBuilder
import jdbchttpsql.model.HttpSqlBridge

//TODO: Args - command line arguments for one time configuration.
fun Array<String>.main() {


    val socialMediaPost = SocialMediaBuilder.Builder()
        .connection("Blue Sky")
        .post("randomly generated")
        .build()

    val runSLE = runSLE()
    return runSLE
}

fun runSLE() {
    val httpSqlBridge = HttpSqlBridge()
    val x = 1
    while (x != 2) {
        httpSqlBridge.sleRadio()
    }
}