package brys.dev.SpotifyWeb.Backend.Auth.types

interface KeyManagerImpl {
    /**
     * Admin key stays static till json is wiped!
     */
    fun makeStaticKey(size: Int): String
    /**
     * Requires the owner key to perform this function
     */
    fun resetKeys()

    fun getKey(): String
}