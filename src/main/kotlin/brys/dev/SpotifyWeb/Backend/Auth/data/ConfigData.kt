package brys.dev.SpotifyWeb.Backend.Auth.data

data class ConfigData(var public: String, var private: String, var useRotatingKeys: Boolean, var keySize: Long, var port: Long) {
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
