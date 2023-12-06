package jdbchttpsql.data

/**
 * Connection builder for social media site, generic
 * @param [connection] - Connection to social media site.
 * @param [post] - Post for (para)social media sites.
 *
 */
class SocialMediaBuilder private constructor(
    val connection: String? = null,
    val post: String? = null,) {

    data class Builder(
        var connection: String? = null,
        var post: String? = null,
        ) {
        fun connection(connection: String) = apply { this.connection = connection }
        fun post(post: String) = apply { this.post = post }
        fun build() = SocialMediaBuilder(connection, post)

        /**
         * Potentially random building, I don't know what purpose there would be to randomize the connection
         * just more a demonstration of possibilities.
         */
        fun randomBuild() = connection(connection ?: "random")
            .connection(connection ?: "connection")
            .post(post ?: "post")
            .build()
    }

}