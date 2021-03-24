package brys.dev.SpotifyWeb.Backend.Cache

import java.lang.StringBuilder

class CacheManager {
    private val trackCache = HashMap<String, StringBuilder>()
    private val playlistCache = HashMap<String, StringBuilder>()
    private val searchCache = HashMap<String, StringBuilder>()
    fun getTrackCache(): HashMap<String, StringBuilder> {
        return trackCache
    }
    fun getPlaylistCache(): HashMap<String, StringBuilder> {
        return playlistCache
    }
    fun getSearchCache(): HashMap<String, StringBuilder> {
        return searchCache
    }
    fun resetCache() {
        trackCache.clear()
        playlistCache.clear()
    }
}