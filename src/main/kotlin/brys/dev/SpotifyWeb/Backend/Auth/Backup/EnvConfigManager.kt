package brys.dev.SpotifyWeb.Backend.Auth.Backup

import brys.dev.SpotifyWeb.Backend.Auth.data.ConfigData
import brys.dev.SpotifyWeb.Server.Util.Util
import jdk.nashorn.internal.runtime.JSType.toLong
import java.lang.Exception

class EnvConfigManager() {
    val data = ConfigData
    private fun searchUpEnv(env: String): String {
        try {
            return System.getenv(env)!!
        } catch (e: Exception) {
             throw e
        }
    }
    fun checkData(): Boolean {
        if (System.getenv("port").isEmpty()) {
            return false
        }
        return true
    }
    init {
        data.keySize = searchUpEnv("key_size").toLong()
        data.port = searchUpEnv("port").toLong()
        data.private = searchUpEnv("spotify_dev_private")
        data.public = searchUpEnv("spotify_dev_public")
        data.useRotatingKeys = searchUpEnv("rotating_keys").toBoolean()
    }
}