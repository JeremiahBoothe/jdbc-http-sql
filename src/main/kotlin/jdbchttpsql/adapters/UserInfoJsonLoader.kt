package jdbchttpsql.adapters

import com.google.gson.Gson
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Loads user information from a specified JSON file.
 *
 * @param filePath The path to the JSON file containing user information.
 * @return The deserialized UserInfo object with the user details.
 */
fun loadUserInfoFromFile(): UserInfo {
val filePath = "D:\\My Documents\\Programming Projects\\IdeaProjects\\jdbchttpsql\\credentials.json"
val jsonContent = File(filePath).readText()

    // Parse the JSON to the Credentials data class
    return Gson().fromJson(jsonContent, UserInfo::class.java)
}