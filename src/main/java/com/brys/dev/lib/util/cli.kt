package com.brys.dev.lib.util


import com.brys.dev.lib.util.cli.c
import com.brys.dev.lib.util.cli.r
import com.github.ajalt.mordant.TermColors
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.system.exitProcess
import com.brys.dev.api.SpotifyWeb

object cli {
    val c = TermColors()
    val r = c.reset
    val run = printAndRun(true)
}
fun printAndRun(logHelp: Boolean) {
    when (logHelp) {
        true -> { help(); runReader() }
        false -> runReader()
    }
}
fun logCacheSize() {
    println(c.brightRed("┌─────────Cache Size──────────┐"))
    println(c.brightBlue(" Playlist Size: ${SpotifyWeb.playlistTracks.size}"))
    println(c.brightBlue(" Track Size: ${SpotifyWeb.tracks.size}"))
    println(c.brightBlue(" Album Size: ${SpotifyWeb.albumTracksCache.size}"))
    println(c.brightRed("└─────────────────────────────┘"))
    printAndRun(false)
}
fun logResetCache() {
    println(c.brightRed("┌─────────Cache Reset──────────┐"))
    println(c.brightBlue(" All ${SpotifyWeb.playlistTracks.size.plus(SpotifyWeb.tracks.size).plus(SpotifyWeb.albumTracksCache.size)} items have been reset"))
    println(c.brightRed("└─────────────────────────────┘"))
    SpotifyWeb.playlistTracks.clear()
    SpotifyWeb.tracks.clear()
    SpotifyWeb.albumTracksCache.clear()
    printAndRun(false)
}
fun logMemory() {
    println(c.brightRed("┌─────────Memory──────────┐"))
    println(c.brightBlue(" Max: $r ${getSize(Runtime.getRuntime().maxMemory())}"))
    println(c.brightBlue(" Total: $r ${getSize(Runtime.getRuntime().totalMemory())}"))
    println(c.brightBlue(" Available: $r ${getSize(Runtime.getRuntime().freeMemory())}"))
    println(c.brightBlue(" Used: $r ${getSize(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())}"))
    println(c.brightRed("└─────────────────────────┘"))
    printAndRun(false)
}
fun logGc() {
    Runtime.getRuntime().gc()
    logMemory()
    printAndRun(false)
}
fun stopApplication() {
    println(c.brightRed("┌────────────STOP─────────────┐"))
    println(c.brightBlue(" Application is being stopped."))
    println(c.brightRed("└─────────────────────────────┘"))
    exitProcess(0)
}
fun logEndpoints() {
    println(c.brightRed("┌────────────────────────────────────────────────Endpoints────────────────────────────────────────────────┐"))
    println(c.brightBlue(" /                   | The default home page"))
    println(c.brightBlue(" /track              | Returns a track json object. Requires a valid track id (/track?id=yourid)"))
    println(c.brightBlue(" /playlist           | Returns a playlist json object. Requires a valid playlist id (/playlist?id=yourid)"))
    println(c.brightBlue(" /album              | Returns a album json object. Requires a valid album id (/album?id=yourid)"))
    println(c.brightBlue(" /artist             | Returns a artist json object. Requires a valid artist id (artist/?id=yourid)"))
    println(c.brightBlue(" /new                | Returns all converted tracks and new hits on spotify"))
    println(c.brightBlue(" /categories         | Get all categories and their names / images"))
    println(c.brightBlue(" /user               | Returns a user json object. Requires a valid username (/user?name=YourUsername)"))
    println(c.brightBlue(" /system/cpu         | Returns cpu thread stats"))
    println(c.brightBlue(" /system/mem         | Returns memory stats"))
    println(c.brightBlue(" /system/gc          | Calls the jvm to attempting to garbage collect"))
    println(c.brightBlue(" /system/cache       | Returns cache stats"))
    println(c.brightBlue(" /system/cache/clear | Clears all cache"))
    println(c.brightRed("└────────────────────────────────────────────────────────────────────────────────────────────────────────┘"))
    printAndRun(false)
}
fun runReader() {
    val consoleReader = BufferedReader(InputStreamReader(System.`in`))
    when (consoleReader.readLine()) {
        "cache_s" -> logCacheSize()
        "cache_r" -> logResetCache()
        "mem_v" -> logMemory()
        "mem_gc" -> logGc()
        "app_stop" -> stopApplication()
        "endpoints" -> logEndpoints()
    }
}
fun help() {
    println(c.brightRed("┌────────────────────────────────────────CLI─────────────────────────────────────────┐"))
    println(c.brightBlue(" cache_s: $r Prints the cache size"))
    println(c.brightBlue(" cache_r: $r Resets all cache for all songs playlists and albums"))
    println(c.brightBlue(" mem_v: $r Views memory usage of the application"))
    println(c.brightBlue(" mem_gc: $r Makes an attempt to garbage collect, this does not clear cache"))
    println(c.brightBlue(" app_stop: $r Stops the webserver and clears all cache"))
    println(c.brightBlue(" endpoints: $r Shows the webservers endpoints"))
    println(c.brightRed("└────────────────────────────────────────────────────────────────────────────────────┘"))
    printAndRun(false)
}
