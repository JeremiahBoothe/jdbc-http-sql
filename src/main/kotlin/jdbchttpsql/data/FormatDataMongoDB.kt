package jdbchttpsql.data

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoCollection
import org.bson.Document

class FormatDataMongoDB(private val collection: MongoCollection<Document>) {

    fun insertMetaData(metaData: FormatData.MetaData) {
        val document = Document(
            mapOf(
                "album" to metaData.album,
                "sku" to metaData.sku,
                "thumb" to metaData.thumb,
                "artist" to metaData.artist,
                "title" to metaData.title,
                "played_show" to metaData.played_show,
                "buy_urls" to metaData.buy_urls,
                "info_urls" to metaData.info_urls,
                "duration" to metaData.duration,
                "guid" to metaData.guid,
                "timestamp" to metaData.timestamp
            )
        )
        collection.insertOne(document)
    }

    fun getMetaDataByGuid(guid: String): FormatData.MetaData {
        val document = collection.find(Filters.eq("guid", guid)).first()
        return document.let {
            FormatData.MetaData(
                album = it.getString("album"),
                sku = it.getString("sku"),
                thumb = it.getString("thumb"),
                artist = it.getString("artist"),
                title = it.getString("title"),
                played_show = it.getString("played_show"),
                buy_urls = it.getString("buy_urls"),
                info_urls = it.getString("info_urls"),
                duration = it.getString("duration"),
                guid = it.getString("guid"),
                timestamp = it.getString("timestamp")
            )
        }
    }
}