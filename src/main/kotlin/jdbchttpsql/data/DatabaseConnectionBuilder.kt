package jdbchttpsql.data

class DatabaseConnectionBuilder
private constructor(
        private val connection: String? = null,
        private val post: String? = null,) {

        data class Builder(
            private var connection: String? = null,
            private var post: String? = null,
        ) {
            fun connection(connection: String) = apply { this.connection = connection }
            fun post(post: String) = apply { this.post = post }
            fun build() = DatabaseConnectionBuilder(connection, post)

            /**
             * RandomBuild, to test functionality with multiple connections randomly.
             */
            fun randomBuild() = connection(connection ?: "random")
                .connection(connection ?: "connection")
                .post(post ?: "post")
                .build()
        }

    }
