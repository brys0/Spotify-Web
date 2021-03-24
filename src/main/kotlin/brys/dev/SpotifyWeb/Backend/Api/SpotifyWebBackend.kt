package brys.dev.SpotifyWeb.Backend.Api

import brys.dev.SpotifyWeb.Backend.Api.data.AudioTrack
import brys.dev.SpotifyWeb.Backend.Auth.data.ConfigData
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.models.Album
import com.adamratzman.spotify.models.Playlist
import com.adamratzman.spotify.models.Track
import com.adamratzman.spotify.spotifyAppApi
import java.lang.Exception

class SpotifyWebBackend(private val config: ConfigData, SpotifyAppApi: SpotifyAppApi)  {
    private val app = SpotifyAppApi

    /**
     * Gets your basic track with audio features attached in a Data Class
     */
     suspend fun getTrack(id: String): AudioTrack? {
        return app.tracks.getTrack(id)?.let { AudioTrack(it, app.tracks.getAudioFeatures(id)) }
    }
    suspend fun searchTracks(query: String, limit: Int): List<Track?> {
        return app.search.searchTrack(query, limit).toList()
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