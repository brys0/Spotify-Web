package brys.dev.SpotifyWeb.Backend.Api.data

import com.adamratzman.spotify.models.*

data class AbstractedAlbum(
    val href: String,
    val id: String,
    val image: String,
    val name: String,
    val type: String,
    val restrictions: Restrictions? = null,
    val releaseDate: String,
    val totalTracks: Int? = null,
    private val albumGroupString: String? = null
) {
}