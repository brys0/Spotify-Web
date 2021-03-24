package brys.dev.SpotifyWeb

fun main() {
    Thread.sleep(1000000000000)
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            println("Shutting Down...")
        }
    })
}