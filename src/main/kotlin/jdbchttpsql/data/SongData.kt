package jdbchttpsql.data

import kotlinx.serialization.Serializable

@Serializable
data class SongData(
    val id: Int,
    val type: String,
    val title: String,
    val album: String,
    val length: Int,
    val genre: String,
    val releaseyear: Int,
    val created_at: String,
    val artist: Artist,
    val started_at: String,
    val ends_at: String
)

@Serializable
data class Artist(
    val name: String
)
