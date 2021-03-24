package brys.dev.SpotifyWeb

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Auth.ConfigManager
import brys.dev.SpotifyWeb.Backend.Auth.KeyManager
import brys.dev.SpotifyWeb.Backend.Auth.data.ConfigData
import brys.dev.SpotifyWeb.Server.Server
import brys.dev.SpotifyWeb.Server.Util.Util
import com.adamratzman.spotify.spotifyAppApi
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.lang.Exception
import kotlin.system.exitProcess
import com.github.ajalt.mordant.TermColors
import java.net.BindException

suspend fun main() {
    val config = ConfigManager().initData()
    val keyManager = KeyManager()
    val logger = Util.Logger
    logger.startHeader("Checks", logger.color.brightRed)
    if (config == null) {
        logger.failure("Webserver has failed loading", "CONFIG", true)
        logger.endHeader(logger.color.brightRed)
        return
    }
    try {
        val key = keyManager.getKey()
        if (key.isEmpty()) {
            keyManager.makeStaticKey(config.keySize.toInt())
        }
    } catch (e: NullPointerException) {
        keyManager.makeStaticKey(config.keySize.toInt())
    }
    logger.success("Config is", "VALID (Auth Key is ${keyManager.getKey()})")
    when (SpotifyWebBackend.Util.verifyToken(config)) {
        false -> {
            logger.failure("Webserver has", "FAILED AUTHENTICATING", true); logger.endHeader(logger.color.brightRed)
        }
        true -> {
            logger.success("Authentication", "SUCCESSFUL")
            try {
                embeddedServer(Netty, config.port.toInt()) {}.start().stop(0L, 0L)
            } catch (e: BindException) {
                logger.failure("Webserver has failed", "STARTING", true); logger.endHeader(logger.color.brightRed)
            }
        }
    }
    val spotify = SpotifyWebBackend(config, spotifyAppApi(config.public, config.private).build())
    logger.success("MiniBoot was", "SUCCESSFUL")
    logger.endHeader(logger.color.brightRed)
    logger.startHeader("Booter", logger.color.brightBlue)
    Server(config, keyManager, spotify).setup()
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            logger.failure("Shutting Down", "SERVER", false)
        }
    })
}


