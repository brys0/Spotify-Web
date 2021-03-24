package brys.dev.SpotifyWeb.Backend.Api.data

import com.adamratzman.spotify.models.AudioFeatures
import com.adamratzman.spotify.models.Track

data class AudioTrack(val track: Track, val features: AudioFeatures)
