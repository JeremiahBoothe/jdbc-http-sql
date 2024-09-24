package jdbchttpsql.sources

import jdbchttpsql.data.ConnectionData

/**
 * Builder class for constructing and accessing database connection details.
 *
 * This class takes a type parameter [T] which must extend [ConnectionData]. It ensures that
 * the essential connection details are accessible, such as the URL, driver, username, and password.
 *
 * @param connectionData An instance of [T] that provides the necessary connection parameters.
 */
class DatabaseSourceBuilder<T : ConnectionData>(private val connectionData: T) {

    private val connectionUrl = "${connectionData.urlDriver}${connectionData.ipAddress}/${connectionData.targetDatabase}"

    fun getUrl(): String = connectionUrl

    fun getDriver(): String = connectionData.urlDriver

    fun getUser(): String = connectionData.userName

    fun getPassword(): String = connectionData.password
}

