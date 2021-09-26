package brys.dev.SpotifyWeb.Backend.Api

import brys.dev.SpotifyWeb.Backend.Api.data.AudioTrack
import brys.dev.SpotifyWeb.Backend.Api.data.AudioTrackAcceptableAsJSON
import brys.dev.SpotifyWeb.Backend.Auth.data.ConfigData
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.models.Album
import com.adamratzman.spotify.models.Playlist
import com.adamratzman.spotify.models.SpotifyPublicUser
import com.adamratzman.spotify.spotifyAppApi

class SpotifyWebBackend(private val config: ConfigData, SpotifyAppApi: SpotifyAppApi) {
    val app = SpotifyAppApi

    /**
     * Gets your basic track with audio features attached in a Data Class
     */
    suspend fun getTrack(id: String): AudioTrack? {
        return app.tracks.getTrack(id)?.let { AudioTrack(AudioTrackAcceptableAsJSON(it.href, it.id, it.uri, it.isPlayable, it.discNumber, it.durationMs, it.explicit, it.name, it.popularity, it.previewUrl, it.trackNumber, it.type, it.episode, it.track), null) }
    }

    /**
     * Get your user
     */
    suspend fun getUser(id: String): SpotifyPublicUser? {
        return app.users.getProfile(id)
    }

    suspend fun searchTracks(query: String, limit: Int, features: Boolean = false): ArrayList<AudioTrack> {
        val tracks = app.search.searchTrack(query, limit).toList()
        val trackList = ArrayList<AudioTrack>()
        return when (features) {
            true -> {
                for (track in tracks) {
                    val feature = track?.id?.let { app.tracks.getAudioFeatures(it) }
                    track?.let {
                        AudioTrack(
                            AudioTrackAcceptableAsJSON(
                                it.href,
                                it.id,
                                it.uri,
                                it.isPlayable,
                                it.discNumber,
                                it.durationMs,
                                it.explicit,
                                it.name,
                                it.popularity,
                                it.previewUrl,
                                it.trackNumber,
                                it.type,
                                it.episode,
                                it.track
                            ), feature
                        )
                    }?.let { trackList.add(it) }
                }
                return trackList
            }
            false -> {
                for (track in tracks) {
                    track?.let {
                        AudioTrack(
                            AudioTrackAcceptableAsJSON(
                                it.href,
                                it.id,
                                it.uri,
                                it.isPlayable,
                                it.discNumber,
                                it.durationMs,
                                it.explicit,
                                it.name,
                                it.popularity,
                                it.previewUrl,
                                it.trackNumber,
                                it.type,
                                it.episode,
                                it.track
                            ), null
                        )
                    }?.let { trackList.add(it) }
                }
                return trackList
            }
        }

    }

    suspend fun getPlaylist(id: String): Playlist? {
        return app.playlists.getPlaylist(id)
    }

    suspend fun getAlblum(id: String): Album? {
        return app.albums.getAlbum(id)
    }


    internal object Util {
        suspend fun verifyToken(config: ConfigData): Boolean {
            try {
                val thread = Thread()
                thread.run {
                    spotifyAppApi(config.public, config.private).build()
                }
                thread.run()
                thread.stop()
            } catch (e: Exception) {
                return false
            }
            return true
        }
    }
}