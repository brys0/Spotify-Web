package brys.dev.SpotifyWeb.Server

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Auth.KeyManager
import brys.dev.SpotifyWeb.Backend.Auth.data.ConfigData
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.admin.CacheController
import brys.dev.SpotifyWeb.Routes.Router
import com.adamratzman.spotify.SpotifyAppApi
import com.github.ajalt.mordant.TermColors
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class Server(val config: ConfigData, val KeyManager: KeyManager, val spotify: SpotifyWebBackend) {
    fun setup() {
        val cache = CacheManager()
        embeddedServer(Netty, config.port.toInt()) {
            routing {
                    Router(this, cache, KeyManager, config, spotify).setup()
            }
        }.start(wait = true)
    }
}