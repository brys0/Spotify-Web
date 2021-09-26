package brys.dev.SpotifyWeb.Backend.Api.data

import com.adamratzman.spotify.models.*

data class AudioTrack(val track: AudioTrackAcceptableAsJSON, val features: AudioFeatures?)
data class AudioTrackAcceptableAsJSON(
    val href: String,
    val id: String,
    val uri: PlayableUri,
    val isPlayable: Boolean = true,
    val discNumber: Int,
    val durationMs: Int,
    val explicit: Boolean,
    val name: String,
    val popularity: Int,
    val previewUrl: String? = null,
    val trackNumber: Int,
    val type: String,
    val episode: Boolean? = null,
    val track: Boolean? = null
)
