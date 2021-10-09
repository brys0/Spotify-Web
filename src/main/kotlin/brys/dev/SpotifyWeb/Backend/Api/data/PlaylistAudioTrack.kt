package brys.dev.SpotifyWeb.Backend.Api.data

import com.adamratzman.spotify.models.AudioFeatures
import com.adamratzman.spotify.models.SpotifyPublicUser
import com.adamratzman.spotify.models.Track

data class PlaylistAudioTrack(val track: AudioTrack, val features: AudioFeatures?, val addedAt: String?, val addedBy: String?, val thumb: String?, val primaryColor: String?)
