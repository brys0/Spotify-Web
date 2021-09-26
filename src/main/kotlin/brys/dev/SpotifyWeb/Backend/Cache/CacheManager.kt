package brys.dev.SpotifyWeb.Backend.Cache

import java.lang.StringBuilder

class CacheManager {
    private val trackCache = HashMap<String, String>()
    private val playlistCache = HashMap<String, String>()
    private val searchCache = HashMap<String, String>()
    fun getTrackCache(): HashMap<String, String> {
        return trackCache
    }
    fun getPlaylistCache(): HashMap<String, String> {
        return playlistCache
    }
    fun getSearchCache(): HashMap<String, String> {
        return searchCache
    }
    fun resetCache() {
        trackCache.clear()
        playlistCache.clear()
    }
}