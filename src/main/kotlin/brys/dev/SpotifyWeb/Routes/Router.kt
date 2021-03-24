package brys.dev.SpotifyWeb.Routes

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Auth.KeyManager
import brys.dev.SpotifyWeb.Backend.Auth.data.ConfigData
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.*
import brys.dev.SpotifyWeb.Backend.Controllers.admin.CacheController
import brys.dev.SpotifyWeb.Backend.Controllers.admin.MemoryController
import brys.dev.SpotifyWeb.Server.Util.Util
import com.adamratzman.spotify.SpotifyAppApi
import com.github.ajalt.mordant.TermColors
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.get
import io.ktor.util.pipeline.*
import java.io.File

/**
 * Router that handles packet handshakes and the physical server
 * @param router Required by the server class for managing routing
 */
class Router(private val router: Routing, private val CacheManager: CacheManager, private val KeyManager: KeyManager, private val config: ConfigData, private val spotify: SpotifyWebBackend) {
    private val logger = Util.Logger
    private var status = Status.OFFLINE
     fun setup() {
         status = Status.BOOTING
         logger.success("Router is now", "ONLINE")
         status = Status.ONLINE
         logger.success("Cache has been", "INITIALIZED")
         logger.success("Main Server has", "BOOTED")
         logger.endHeader(logger.color.brightBlue)
         router {
            get("/") {
                HomeController().response(this)
            }
            get("/track") {
                TrackController(spotify, CacheManager).response(this)
            }
            get("/cache") {
                CacheController(CacheManager, KeyManager().getKey()).response(this)
            }
            get("/memory") {
                MemoryController(KeyManager().getKey()).response(this)
            }
            get("/search") {
                SearchController(spotify, CacheManager).response(this)
            }
            get("/playlist") {
                PlaylistController(spotify, CacheManager).response(this)
            }
            get("/album") {
                AlbumController(spotify, CacheManager).response(this)
            }

        }
    }
    enum class Status {
        OFFLINE, ONLINE, BOOTING
    }
}