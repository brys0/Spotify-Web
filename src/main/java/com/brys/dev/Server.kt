package com.brys.dev
import org.springframework.web.bind.annotation.RestController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.lang.StringBuilder
import java.util.*
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import com.adamratzman.spotify.models.Track
import com.brys.dev.api.SpotifyWeb
import com.brys.dev.api.methods
import com.brys.dev.lib.util.Checks
import com.brys.dev.lib.util.cli
import com.brys.dev.lib.util.statistics
import com.github.ajalt.mordant.TermColors
import com.google.gson.Gson
import java.io.File
import java.lang.Exception

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class, ErrorMvcAutoConfiguration::class])
class Application()
val t = TermColors()
fun main(args: Array<String>) {
    println("${t.brightYellow.bg} ${t.black}~ ${t.reset} ${t.brightWhite}- Please wait while the webserver does its checks${t.reset}")
    Checks.check
    try {
        runApplication<Application>()
    } catch (e: Exception) {
        println("${t.brightRed.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Error occurred in main application${t.reset}")
    }
    println("${t.brightYellow.bg} ${t.black}~ ${t.reset} ${t.brightWhite}- Webserver is booting${t.reset}")
    println("${t.rgb("#34eb40").bg} ${t.black}+ ${t.reset} ${t.brightWhite}- Webserver online at localhost${t.reset}")
    cli.run
}


@RestController
class DefaultController {
    @GetMapping(value = ["/"])
    fun default(): String {
        println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}/${t.reset}")
       return "<!DOCTYPE html>\n" +
               "<html>\n" +
               "<body>\n" +
               "<title>Spotify Web</title>\n" +
               "<div class=\"img\"><img src=\"https://raw.githubusercontent.com/brys0/Spotify-Web/master/Art/sws-spotify.png\" alt=\"img\"></div>\n" +
               "<a href=\"https://github.com/brys0/Spotify-Web\">\n" +
               "    <div class=\"gh\">Github</div>\n" +
               "</a>\n" +
               "<div class=\"setup\">Congrats on getting the Webserver up and running! \uD83C\uDF89</div>\n" +
               "<style>\n" +
               "        @import url('https://fonts.googleapis.com/css2?family=Audiowide&display=swap');\n" +
               "        @import url('https://fonts.googleapis.com/css2?family=Montserrat&display=swap');\n" +
               "        body {\n" +
               "            background-color: #2C2F33;\n" +
               "        -webkit-animation: fadein 2.5s;\n" +
               "       -moz-animation: fadein 2.5s;\n" +
               "        -ms-animation: fadein 2.5s;\n" +
               "         -o-animation: fadein 2.5s;\n" +
               "            animation: fadein 2.5s;\n" +
               "        }\n" +
               "        .img {\n" +
               "            position: fixed;\n" +
               "            top: 20%;\n" +
               "            left: 50%;\n" +
               "            transform: translate(-50%, -50%);\n" +
               "        }\n" +
               "        .gh {\n" +
               "            font-family: 'Audiowide', regular;\n" +
               "            font-size: 150px;\n" +
               "            color: #7289DA;\n" +
               "            position: fixed;\n" +
               "            top: 50%;\n" +
               "            left: 40%;\n" +
               "            transition: all 300ms ease-in-out;\n" +
               "        }\n" +
               "        .gh:hover {\n" +
               "            color: #1DB954;\n" +
               "            transition: all 300ms ease-in-out;\n" +
               "        }\n" +
               "        .setup {\n" +
               "            color: #eb4034;\n" +
               "            font-family: 'Montserrat', sans-serif;\n" +
               "            font-size: 45px;\n" +
               "            position: fixed;\n" +
               "            top: 75%;\n" +
               "            left: 30%;\n" +
               "        }\n" +
               "\n" +
               "        @keyframes fadein {\n" +
               "    from { opacity: 0; }\n" +
               "    to   { opacity: 1; }\n" +
               "}\n" +
               "@-moz-keyframes fadein {\n" +
               "    from { opacity: 0; }\n" +
               "    to   { opacity: 1; }\n" +
               "}\n" +
               "@-webkit-keyframes fadein {\n" +
               "    from { opacity: 0; }\n" +
               "    to   { opacity: 1; }\n" +
               "}\n" +
               "@-ms-keyframes fadein {\n" +
               "    from { opacity: 0; }\n" +
               "    to   { opacity: 1; }\n" +
               "}\n" +
               "@-o-keyframes fadein {\n" +
               "    from { opacity: 0; }\n" +
               "    to   { opacity: 1; }\n" +
               "}\n" +
               "    </style>\n" +
               "</body>\n" +
               "</html>"
    }
}

/**
 * Get and returns [Track] information as easy to read **JSON** format 
 * * How do I use this ***[Endpoint](https://stackoverflow.com/questions/2122604/what-is-an-endpoint)***
 * 1. Head to **your_spotify_parser_server_ip**:**your_port**\track?id=**your_track_id**
 * 2. See what data is returned commonly it will look like Track {}
 * 3. Parse the data out from the **JSON**
 * ***[NodeJS](https://shouts.dev/parse-json-in-node-js-from-external-url)***,
 * ***[Python](https://pythonbasics.org/json/)***,
 * ***[Kotlin](https://stackoverflow.com/questions/44883593/how-to-read-json-from-url-using-kotlin-android)***,
 * ***[Java](https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java)***,
 * ***[C# (CSharp)](https://stackoverflow.com/questions/22550738/how-to-get-json-string-from-the-url/22550937)***,
 * ***[C++ (CPlusplus)](https://gist.github.com/connormanning/41efa6075515019e499c)***,
 * ***[Ruby](https://stackoverflow.com/questions/18581792/ruby-on-rails-and-json-parser-from-url)***,
 * ***[Batch](https://stackoverflow.com/questions/50811698/how-to-get-json-from-curl-as-array-in-a-batch-file)***
 * * If response data is entirely ***[null]*** it failed finding your search id check if the search id matches what was put in the header **id=**
 */
@RestController
class TrackController {
    @GetMapping(value = ["/track"])
    fun track(@RequestParam(value = "id") id: String): StringBuilder {
        val track = SpotifyWeb.getRawTrack(id)
        println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}track${t.reset}${t.white} with track id: $id${t.reset}")
        return StringBuilder()
            .append("{")
            .append("\"name\": \"${track?.name}\",")
            .append("\n\"artwork\": \"${track?.album?.images?.get(0)?.url}\",")
            .append("\n\"artist\": \"${track?.artists?.get(0)?.name}\",")
            .append("\n\"popularity\": ${track?.popularity},")
            .append("\n\"explicit\": ${track?.explicit},")
            .append("\n\"duration\": ${track?.durationMs},")
            .append("\n\"track_num\": ${track?.trackNumber},")
            .append(
                "\n\"converted_trk\": \"${track?.name} - ${
                    track?.artists?.get(
                        0
                    )?.name
                }\""
            )
            .append("}")
    }
}
@RestController
class PlaylistController {
    @GetMapping(value = ["/playlist"])
    fun playlist(@RequestParam(value = "id") id: String): StringBuilder? {
        println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}playlist${t.reset}${t.white} with playlist id: $id${t.reset}")
        val pt = SpotifyWeb.getPlaylistTracks(id)
        val p = SpotifyWeb.getRawPlaylist(id)
        val jsonarr = Gson().toJsonTree(pt)
        return StringBuilder()
            .append("{")
            .append("\"name\": \"${p?.name}\",")
            .append("\"owner\": \"${p?.owner?.displayName}\",")
            .append("\"description\": \"${p?.description}\",")
            .append("\"followers\": ${p?.followers?.total},")
            .append("\"image\": \"${p?.images?.get(0)?.url}\",")
            .append("\"snapshot\": \"${p?.snapshot?.snapshotId}\",")
            .append("\"tracks\": $jsonarr")
            .append("}")
    }
}
@RestController
class AlbumController {
    @GetMapping(value = ["/album"])
    fun album(@RequestParam(value = "id") id: String): StringBuilder? {
        println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}album${t.reset}${t.white} with album id: $id${t.reset}")
        val at = SpotifyWeb.getAlbumTracks(id)!!
        val a = SpotifyWeb.getRawAlbum(id)!!
        val jsonarr = Gson().toJsonTree(at)
        return StringBuilder()
            .append("{")
            .append("\"name\": \"${a.name}\",")
            .append("\"artists\": \"${a.artists[0].name}\",")
            .append("\"release_date\": ${a.releaseDate.year},")
            .append("\"popularity\": ${a.popularity},")
            .append("\"image\": \"${a.images[0].url}\",")
            .append("\"label\": \"${a.label}\",")
            .append("\"total_tracks\": ${a.totalTracks},")
            .append("\"tracks\": $jsonarr")
            .append("}")
    }
}
@RestController
class Artist {
    @GetMapping(value = ["/artist"])
    fun artist(@RequestParam(value = "id") id: String): StringBuilder? {
        println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}artist${t.reset}${t.white} with artist id: $id${t.reset}")
        val artist = SpotifyWeb.getArtist(id)!!
        val tracks = SpotifyWeb.getAPI().artists.getArtistTopTracks(id).complete()
        val related = SpotifyWeb.getAPI().artists.getRelatedArtists(id).complete()
        val trackList = mutableListOf<String>()
        val relatedList = mutableListOf<String>()
        for (i in 0 until 10) {
            val track = tracks[i]
            trackList.add(i, "${track.name} - ${track.artists[0].name}")
        }
        for (i in 0 until 5) {
            val artistName = related[i].name
            relatedList.add(i, artistName)
        }
        val genres = Gson().toJsonTree(artist.genres)
        val tracksJs = Gson().toJsonTree(trackList)
        return StringBuilder()
            .append("{")
            .append("\"name\": \"${artist.name}\",")
            .append("\"followers\": ${artist.followers.total},")
            .append("\"genres\": ${genres},")
            .append("\"image\": \"${artist.images[0].url}\",")
            .append("\"top_tracks\": $tracksJs")
            .append("}")
    }
    @RestController
    class NewTracks {
        @GetMapping(value = ["/new"])
        fun new(): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}new${t.reset}${t.white}")
            val browse = methods.top
            val trackArr = Gson().toJsonTree(browse)
            return StringBuilder()
                .append("{")
                .append("\"Track_Rec\": $trackArr")
                .append("}")
        }
    }
    @RestController
    class browseCategories {
        @GetMapping(value = ["/categories"])
        fun categories(): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}categories${t.reset}${t.white}")
            val method = methods
            val imgs = method.categoryImages
            val names = method.categoryNames
            val imgData = Gson().toJsonTree(imgs)
            val nameData = Gson().toJsonTree(names)
            return StringBuilder()
                .append("{")
                .append("\"names\": $nameData,")
                .append("\"images\": $imgData")
                .append("}")
        }
    }
    @RestController
    class user {
        @GetMapping(value = ["/user"])
        fun user(@RequestParam(value = "name") name: String): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}user with name $name${t.reset}${t.white}")
            val user = SpotifyWeb.getUser(name)!!
            return StringBuilder()
                .append("{")
                .append("\"name\": \"${user.displayName}\",")
                .append("\"followers\": ${user.followers.total},")
                .append("\"image\": \"${user.images[0].url}\"")
                .append("}")
        }
    }
    @RestController
    class CPU {
        @GetMapping(value = ["/system/cpu"])
        fun gpu(): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}System/CPU${t.reset}${t.white}")
            val stats = statistics.cpu
            return StringBuilder()
                .append("{")
                .append("\"alive\": ${stats.liveThreads},")
                .append("\"parked\": ${stats.parkedThreads}")
                .append("}")
        }
    }
    @RestController
    class Memory {
        @GetMapping(value = ["/system/mem"])
        fun mem(): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}System/MEM${t.reset}${t.white}")
            val stats = statistics.memStats
            return StringBuilder()
                .append("{")
                .append("\"total\": \"${stats.total}\",")
                .append("\"max\": \"${stats.max}\",")
                .append("\"available\": \"${stats.available}\",")
                .append("\"used\": \"${stats.used}\"")
                .append("}")
        }
    }
    @RestController
    class GC {
        @GetMapping(value = ["/system/gc"])
        fun gc(): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}System/MEM/GC${t.reset}${t.white}")
            val stats = statistics.memStats
            Runtime.getRuntime().gc()
            return StringBuilder()
                .append("{")
                .append("\"gc\": true")
                .append("}")
        }
    }
    @RestController
    class Cache {
        @GetMapping(value = ["/system/cache"])
        fun cache(): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}System/CACHE${t.reset}${t.white}")
            SpotifyWeb.playlistTracks.size
            SpotifyWeb.albumTracksCache.size
            return StringBuilder()
                .append("{")
                .append("\"tracks\": ${SpotifyWeb.tracks.size},")
                .append("\"playlists\": ${SpotifyWeb.playlistTracks.size},")
                .append("\"albums\": ${SpotifyWeb.albumTracksCache.size}")
                .append("}")
        }
    }
    @RestController
    class CacheClear {
        @GetMapping(value = ["/system/cache/clear"])
        fun clear(): StringBuilder? {
            println("${t.brightBlue.bg} ${t.black}- ${t.reset} ${t.white}- Request was made for ${t.brightMagenta}System/CACHE/CLEAR${t.reset}${t.white}")
            SpotifyWeb.playlistTracks.clear()
            SpotifyWeb.tracks.clear()
            SpotifyWeb.albumTracksCache.clear()
            return StringBuilder()
                .append("{")
                .append("\"cache\": true")
                .append("}")
        }
    }
}