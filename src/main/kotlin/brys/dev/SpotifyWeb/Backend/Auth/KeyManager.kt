package brys.dev.SpotifyWeb.Backend.Auth

import brys.dev.SpotifyWeb.Backend.Auth.types.KeyManagerImpl
import java.io.File
import java.io.FileWriter

class KeyManager: KeyManagerImpl {
    override fun makeStaticKey(size: Int): String {
        val key = Keys.makeKey(size)
        Keys.storeKey(key)
        return key
    }

    override fun resetKeys() {
        Keys.deleteKey()
        return
    }

    override fun getKey(): String {
        return Keys.retrieveKey()
    }

    internal object Keys {
        fun makeKey(size: Int): String {
            val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
            return (1..size)
                .map { charset.random() }
                .joinToString("")
        }

        fun storeKey(key: String) {
            when (File("key").exists()) {
                true -> {
                    val writer = FileWriter("key")
                    writer.write(key)
                    writer.close()
                }
                false -> {
                    File("key").createNewFile()
                    val writer = FileWriter("key")
                    writer.write(key)
                    writer.close()
                }
            }
        }

        fun deleteKey(): Boolean {
            if (File("key").exists()) {
                return File("key").delete()
            }
            return false
        }


        fun retrieveKey(): String {
            return when (File("key").exists()) {
                false -> throw NullPointerException("File does not exist!")
                true -> File("key").readText()
            }
        }
    }
}

