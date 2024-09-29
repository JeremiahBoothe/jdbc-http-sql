package jdbchttpsql.data

import kotlinx.serialization.Serializable

/**
 * Represents metadata for a song, including details such as the song's ID, title, album, genre, and artist information.
 *
 * @property id A unique identifier for the song.
 * @property type The type or category of the song, if applicable.
 * @property title The title of the song.
 * @property album The album to which the song belongs.
 * @property length The length or duration of the song in seconds.
 * @property genre The genre of the song, if specified.
 * @property releaseYear The year the song was released.
 * @property createdAt The timestamp of when the song entry was created.
 * @property artist The artist who performed or created the song.
 * @property startedAt The starting timestamp for when the song began playing.
 * @property endsAt The ending timestamp for when the song stopped playing.
 */
@Serializable
data class SongData(
    val id: Int,
    val type: String?,
    val title: String?,
    val album: String?,
    val length: Int?,
    val genre: String?,
    val releaseYear: Int?,
    val createdAt: String?,
    val artist: Artist?,
    val startedAt: String?,
    val endsAt: String?
)

/**
 * Represents an artist in the music metadata.
 *
 * @property name The name of the artist.
 */
@Serializable
data class Artist(
    val name: String?
)
