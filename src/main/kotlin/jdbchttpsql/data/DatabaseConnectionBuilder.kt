package jdbchttpsql.data

class DatabaseConnectionBuilder
private constructor(
        val connection: String? = null,
        val post: String? = null,) {

        data class Builder(
            var connection: String? = null,
            var post: String? = null,
        ) {
            fun connection(connection: String) = apply { this.connection = connection }
            fun post(post: String) = apply { this.post = post }
            fun build() = DatabaseConnectionBuilder(connection, post)

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
