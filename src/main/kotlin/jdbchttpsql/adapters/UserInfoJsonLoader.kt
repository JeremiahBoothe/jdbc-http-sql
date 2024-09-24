package jdbchttpsql.adapters

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Loads user information from a specified JSON file.
 *
 * @param filePath The path to the JSON file containing user information.
 * @return The deserialized UserInfo object with the user details.
 */
fun loadUserInfoFromFile(filePath: String): UserInfo {
    // Read the JSON file as a string
    val jsonContent = File(filePath).readText()

    // Deserialize the JSON content into a UserInfo object
    return Json.decodeFromString(jsonContent)
}