package com.brys.dev.api
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.models.Album
import com.adamratzman.spotify.models.Artist
import com.adamratzman.spotify.models.Playlist
import com.adamratzman.spotify.models.SimpleAlbum
import com.adamratzman.spotify.spotifyAppApi
import com.github.ajalt.mordant.TermColors
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import kotlin.system.exitProcess

/**
 * Object that provides different spotify endpoints
 * ##### Different functions:
 * * [getTrack]
 * * [getRawTrack]
 * * [getTop]
 * * [getPlaylistTracks]
 * * [getAPI]
 */
object SpotifyWeb{
    private var conf = conf()
    private val uniapi = spotifyAppApi(conf[0], conf[1]).build()
    private val color = TermColors()
    val tracks = HashMap<String, String>()
    val playlistTracks = HashMap<String, MutableList<String>>()
    val albumTracksCache = HashMap<String, MutableList<String>>()

    /**
     * Takes a trackname string and returns the track name to be searched by [YouTubeBasic]
     */
    fun getTrack(args: String): String? {
        if (tracks.containsKey(args)) {
            return tracks[args]
        }
        val returns = uniapi.tracks.getTrack(args).complete()
        if (returns?.name == null) {
            return returns?.name
        }
        tracks[returns.id] = returns.name
        println("${color.rgb("#34eb40").bg} ${color.black}+ ${color.reset} ${color.brightWhite}- Track Added to cache ${tracks.size-1} -> ${tracks.size}${color.reset}")
        return returns.name
    }
    /**
     * Takes a trackname string and returns a raw modeled track To be parsed elsewhere
     */
    fun getRawTrack(args: String): com.adamratzman.spotify.models.Track? {
        val parseid = args.replace("https://open.spotify.com/track/", "")
        val returns = uniapi.tracks.getTrack(parseid).complete()
        return returns
    }
    /**
     * Gets top releases on spotify and returns [SimpleAlbum]
     */
    fun getTop(): SimpleAlbum {
        val returns = uniapi.browse.getNewReleases(1).complete()
        return returns[0]
    }
    /**
     * Gets and calls returns a list of track names in playlist
     */
    fun getPlaylistTracks(args: String): MutableList<String>? {
        if (playlistTracks.containsKey(args)) {
            return playlistTracks[args]
        }
        val returns = uniapi.playlists.getPlaylist(args).complete()
        val list = mutableListOf<String>()
        for (i in 0 until returns?.tracks?.size!!) {
            val track = returns.tracks[i]
            val trackid = track.track?.id!!
            getTrack(trackid)?.let { list.add(i , "$it - ${getRawTrack(trackid)!!.artists[0].name}") }
        }
        playlistTracks[returns.id] = list
        println("${color.rgb("#34eb40").bg} ${color.black}+ ${color.reset} ${color.brightWhite}- Playlist Added to cache ${playlistTracks.size-1} -> ${playlistTracks.size}${color.reset}")
        return list
    }

    /**
     * Gets a raw playlist data class
     */
    fun getRawPlaylist(args: String): Playlist? {
        return uniapi.playlists.getPlaylist(args).complete()
    }
    /**
     * Gets a spotify album and caches songs
     */
    fun getAlbumTracks(args: String): MutableList<String>? {
        if (albumTracksCache.containsKey(args)) {
            return albumTracksCache[args]
        }
        val albumTracks = uniapi.albums.getAlbumTracks(args).complete()
        val album = uniapi.albums.getAlbum(args).complete()
        val list = mutableListOf<String>()
        for (i in 0 until albumTracks.size) {
            val track = albumTracks[i]
            val trackName = track.name
            list.add(i, trackName)
        }
        albumTracksCache[album!!.id] = list
        println("${color.rgb("#34eb40").bg} ${color.black}+ ${color.reset} ${color.brightWhite}- Album Added to cache ${albumTracksCache.size-1} -> ${albumTracksCache.size}${color.reset}")
        return list
    }
    fun getRawAlbum(args: String): Album? {
        return uniapi.albums.getAlbum(args).complete()
    }
    fun getArtist(args: String): Artist? {
        return uniapi.artists.getArtist(args).complete()
    }
    fun getTopTracks(): MutableList<String> {
        val browse = uniapi.browse.getNewReleases(35).complete()
        val list = mutableListOf<String>()
        for (i in 0 until browse.size) {
            val track = browse[i]
            list.add(i, "${track.name} - ${track.artists[0].name}" )
        }
        return list
    }
    /**
     * Returns the instituted API
     */
    fun getAPI(): SpotifyAppApi {
        return uniapi
    }
    /**
     * Init Conf
     */
    private fun conf(): Array<String> {
        val t  = TermColors()
        try {
            JSONParser().parse(FileReader("conf.json"))
        } catch (e: FileNotFoundException) {
            println("${t.brightYellow.bg} ${t.black}~ ${t.reset} ${t.brightWhite}- conf.json was not found, creating and exiting..${t.reset}")
            File("conf.json").createNewFile()
            val writer = FileWriter("conf.json")
            writer.write("{\n" +
                    "  \"public\": \"public client\",\n" +
                    "  \"private\": \"private client\"\n" +
                    "}")
            writer.close()
            exitProcess(0)
        }
        val obj: Any = JSONParser().parse(FileReader("conf.json"))
        val jo: org.json.simple.JSONObject = obj as org.json.simple.JSONObject
        val public = jo["public"] as String
        val private = jo["private"] as String
        if (public.isEmpty() or private.isEmpty()) {
            println("${t.brightRed.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Values in json were not found, exiting..${t.reset}")
            exitProcess(0)
        }
        return arrayOf(public, private)
    }
}