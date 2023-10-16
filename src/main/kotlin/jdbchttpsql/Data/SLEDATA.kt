package jdbchttpsql.Data

import org.ktorm.dsl.*
import org.ktorm.schema.*

class SLEDATA {
    data class MetaData(
        val album: String,
        val sku: String,
        val thumb: String,
        val artist: String,
        val title: String,
        val played_show: String,
        val buy_urls: String,
        val info_urls: String,
        val duration: String,
        val guid: String,
        val timestamp: String,)

    object RadioMetaData : BaseTable<MetaData>("radio_jar_metadata_stream") {
        val album = varchar("album")
        val sku = varchar("sku")
        val thumb = varchar("thumb")
        val artist = varchar("artist")
        val title = varchar("title")
        val played_show = varchar("played_show")
        val buy_urls = varchar("buy_urls")
        val info_urls = varchar("info_urls")
        val duration = varchar("duration")
        val guid = varchar("guid").primaryKey()
        val timestamp = varchar("time_stamp")

        override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = MetaData(
            album = row[album].orEmpty(),
            sku = row[sku].orEmpty(),
            thumb = row[thumb].orEmpty(),
            artist = row[artist].orEmpty(),
            title = row[title].orEmpty(),
            played_show = row[played_show].orEmpty(),
            buy_urls = row[buy_urls].orEmpty(),
            info_urls = row[info_urls].orEmpty(),
            duration = row[duration].orEmpty(),
            guid = row[guid].orEmpty(),
            timestamp = row[timestamp].orEmpty(),)
    }
}