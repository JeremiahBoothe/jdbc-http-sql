package jdbchttpsql.sources

import jdbchttpsql.data.ConnectionData

class DatabaseSourceBuilder<T : ConnectionData>(private val connectionData: T) {

    private val connectionUrl = "${connectionData.urlDriver}${connectionData.ipAddress}/${connectionData.targetDatabase}"

    fun getUrl(): String = connectionUrl

    fun getDriver(): String = connectionData.urlDriver

    fun getUser(): String = connectionData.username

    fun getPassword(): String = connectionData.password
}

