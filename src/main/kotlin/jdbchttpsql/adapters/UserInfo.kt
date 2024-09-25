package jdbchttpsql.adapters

import io.ktor.server.routing.RoutingPath.Companion.root
import kotlinx.serialization.Serializable

/**
 * Data class representing user information.
 *
 * This class is used to serialize and deserialize user credentials, specifically the username
 * and password. It is implemented using the `@Serializable` annotation to indicate that instances
 * of this class can be serialized and deserialized using Kotlin serialization.
 *
 * @property userName The username for user authentication.
 * @property password The password for user authentication.
 */
@Serializable
data class UserInfo(
    val userName: String,
    val password: String
)