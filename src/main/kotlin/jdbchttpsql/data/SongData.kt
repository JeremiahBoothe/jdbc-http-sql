package jdbchttpsql.data

import kotlinx.serialization.Serializable
import org.ktorm.schema.BaseTable

/**
 *  @author Jeremiah Boothe
 *  @object [RadioMetaData]
 *  @constructor [MetaData]
 *  @see [RadioMetaData]
 *  @return nothing
 *
 */
class SongData {
    /**
     * Connect to a database using the specific connection arguments.
     * @see Serializable
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
    @Serializable
    data class SongData(
        val id: Int,
        val type: String?,
        val title: String?,
        val album: String?,
        val length: Int?,
        val genre: String?,
        val releaseyear: Int?,
        val created_at: String?,
        val artist: Artist?,
        val started_at: String?,
        val ends_at: String?
    )
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
    @Serializable
    data class Artist(
        val name: String?
    )
}