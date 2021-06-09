package brys.dev.SpotifyWeb.Backend.Auth

import brys.dev.SpotifyWeb.Backend.Auth.Backup.EnvConfigManager
import brys.dev.SpotifyWeb.Backend.Auth.data.ConfigData
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import kotlin.system.exitProcess

class ConfigManager {
    private val parser = JSONParser()
    var data = ConfigData
    fun initData(): ConfigData? {
       when (File("./conf.json").exists()) {
           true -> {
               return try {
                   val json = parser.parse(FileReader("./conf.json"))
                   val obj: JSONObject = json as JSONObject
                   data.keySize = obj["key_size"] as Long
                   data.private = obj["spotify_dev_private"] as String
                   data.public = obj["spotify_dev_public"] as String
                   data.useRotatingKeys = obj["rotating_keys"] as Boolean
                   data.port = obj["port"] as Long
                   if (data.keySize.toInt() == 0) {
                       return null
                   }
                   data.init()
               } catch (e: Exception) {
                   return null
               }
           }
           false -> {
               try {
                   if (EnvConfigManager().checkData()) {
                       data = EnvConfigManager().data
                   } else {
                       File("./conf.json").createNewFile()
                       val writer = FileWriter("./conf.json")
                       writer.write(File("./resources/typing.json").readText())
                       writer.close()
                   }
               } catch (e: Exception) {
                   e.printStackTrace()
               }
           }
       }
        return data.init()
    }
}