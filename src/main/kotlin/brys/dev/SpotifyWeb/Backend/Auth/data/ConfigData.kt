package brys.dev.SpotifyWeb.Backend.Auth.data

data class ConfigData(val public: String, val private: String, val useRotatingKeys: Boolean, val keySize: Long, val port: Long) {
    /**
     * Companion object for ConfigData used for initialization
     */
    companion object {
        var public: String = ""
        var private: String = ""
        var useRotatingKeys: Boolean = false
        var keySize: Long = 0
        var port: Long = 0
            fun init(): ConfigData {
                return ConfigData(public, private, useRotatingKeys, keySize, port)
            }
    }
}
