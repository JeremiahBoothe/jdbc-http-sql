package jdbchttpsql.adapters

import kotlinx.serialization.Serializable

/**
 * Data class representing user information.
 *
 * This class is used to store the username and password for user authentication.
 * The `@Serializable` annotation allows for easy serialization and deserialization of instances
 * using Kotlinx Serialization.
 *
 * @property userName The username associated with the user, defaulting to "jeremiah".
 * @property password The password associated with the user, defaulting to "contranym!3".
 */
@Serializable
data class UserInfo(
    val userName: String = "jeremiah",
    val password: String = "contranym!3"
)