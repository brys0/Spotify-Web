package brys.dev.SpotifyWeb.Backend.Api.data

import com.adamratzman.spotify.endpoints.client.PlaylistSnapshot
import com.adamratzman.spotify.models.*

public data class PlaylistWithoutTracks(
    val href: String,
    val id: String,
    val uri: PlaylistUri,
    val collaborative: Boolean,
    val description: String? = null,
    val followers: Followers,
    val primaryColor: String? = null,
    val images: List<SpotifyImage>,
    val name: String,
    val owner: SpotifyPublicUser,
    val public: Boolean? = null,
    val type: String
)


public data class PlaylistWithInternalTracks(
    val href: String,
    val id: String,
    val uri: PlaylistUri,
    val collaborative: Boolean,
    val description: String? = null,
    val followers: Followers,
    val primaryColor: String? = null,
    val images: List<SpotifyImage>,
    val name: String,
    val tracks: MutableCollection<PlaylistAudioTrack>,
    val owner: SpotifyPublicUser,
    val public: Boolean? = null,
    val type: String
)

