package jdbchttpsql.adapters

/**
 * A class used to build instances of `DatabaseConnectionBuilder`.
 * The builder class helps in building a `DatabaseConnectionBuilder`
 * instance with specific configurations for a database connection.
 */
class DatabaseConnectionBuilder
private constructor(
        private val connection: String? = null,
        private val post: String? = null,) {
    /**
     * A builder class for creating instances of `DatabaseConnectionBuilder`.
     * It allows the configuration of database connection parameters.
     *
     * @property connection the connection string to use for the database connection.
     * @property post the post connection string to use for the database connection.
     */

    data class Builder(
            private var connection: String? = null,
            private var post: String? = null,
        ) {
            private fun connection(connection: String) = apply { this.connection = connection }
            private fun post(post: String) = apply { this.post = post }
            private fun build() = DatabaseConnectionBuilder(connection, post)

        /**
         * Configures and builds a `DatabaseConnectionBuilder` instance with default or
         * provided values. If the parameters `connection` and `post` are not explicitly
         * set, default values will be used.
         *
         * @return A configured instance of `DatabaseConnectionBuilder`.
         */
        fun randomBuild() = connection(connection ?: "random")
                .connection(connection ?: "connection")
                .post(post ?: "post")
                .build()
        }

    }
