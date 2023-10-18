package jdbchttpsql.data

import org.ktorm.dsl.*
import org.ktorm.schema.*
import java.util.*

/**
 *  @author Jeremiah Boothe
 *  @object [RadioMetaData]
 *  @constructor [MetaData]
 *  @see [RadioMetaData]
 *  @return nothing
 *
 */
class FormatData {
    /**
     * Connect to a database using the specific connection arguments.
     *
     * @param album the album name of the currently playing album.
     * @param sku the sku of the currently playing song.
     * @param thumb the thumbnail url of the currently playing album.
     * @param artist the name of the artist currently playing artist.
     * @param title the name of the currently playing song.
     * @param played_show the name of the radio show currently playing the song.
     * @param buy_urls the url to buy the album or song.
     * @param info_urls the url with artist, song, and/or album info.
     * @param duration the play duration of the currently playing song.
     * @param guid the radio station id for the currently playing song used as primary key.
     * @param timestamp when the currently playing song was played to keep track of streams.
     * @return the new-created database object.
     */
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

    /**
     * Maps data class to BaseTable
     * @constructor [RadioMetaData]
     * @param album the album name of the currently playing album.
     * @param sku the sku of the currently playing song.
     * @param thumb the thumbnail url of the currently playing album.
     * @param artist the name of the artist currently playing artist.
     * @param title the name of the currently playing song.
     * @param played_show the name of the radio show currently playing the song.
     * @param buy_urls the url to buy the album or song.
     * @param info_urls the url with artist, song, and/or album info.
     * @param duration the play duration of the currently playing song.
     * @param guid the radio station id for the currently playing song used as primary key.
     * @param timestamp when the currently playing song was played to keep track of streams.
     * @see BaseTable
     */
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