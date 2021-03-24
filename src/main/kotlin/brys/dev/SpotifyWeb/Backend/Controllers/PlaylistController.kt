package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.lang.StringBuilder

class PlaylistController(private val spotify: SpotifyWebBackend, private val cache: CacheManager): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>) {
        val json = StringBuilder()
        val id = call.context.parameters["id"] ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.BadRequest, text = "{\"message\": \"Playlist ID Required\"}")
        val new = call.context.parameters["skipcache"]
        val backend = spotify
        if(id.isEmpty()) {
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"Playlist ID Required\"}")
        }
        if (new == "true") {
            val playlist = backend.getPlaylist(id) ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"No track with that id was found\"}")
            json.append("{ \"playlist\": {\"name\": \"${playlist.name}\", \"owner\": \"${playlist.owner}\", \"description\": \"${playlist.description}\", \"followers\": ${playlist.followers.total}, \"image\": \"${playlist.images[0].url}\", \"tracks\": [")
            for (i in playlist.tracks) {
                val audioTrack = i.track?.id?.let { backend.getTrack(it) }
                val isComma = if (i.track == playlist.tracks.last()?.track) "" else ","
                json.append("{ \"track\": {\"name\": \"${audioTrack!!.track.name}\", \"artwork\": \"${audioTrack.track.album.images[0].url}\", \"artist\": \"${audioTrack.track.artists[0].name}\", \"popularity\": ${audioTrack.track.popularity}, \"explicit\": ${audioTrack.track.explicit}, \"full_track\": \"${audioTrack.track.name} - ${audioTrack.track.artists[0].name}\", \"features\": {\"dance\": ${audioTrack.features.danceability}, \"energy\": ${audioTrack.features.energy}, \"key\": ${audioTrack.features.key}, \"loud\": ${audioTrack.features.loudness}, \"mode\": ${audioTrack.features.mode}, \"speech\": ${audioTrack.features.speechiness}, \"acoustic\": ${audioTrack.features.acousticness}, \"instrument\": ${audioTrack.features.instrumentalness}, \"live\": ${audioTrack.features.liveness}, \"valence\": ${audioTrack.features.valence}, \"tempo\": ${audioTrack.features.tempo}, \"duration\": ${audioTrack.features.durationMs}}, \"url\": \"https://open.spotify.com/track/${audioTrack.track.id}\"}}$isComma")
            }
            json.append("]}}")
            return call.context.respondText(contentType = ContentType.Application.Json, text = json.toString())
        }
        if (cache.getPlaylistCache().containsKey(id)) {
            return call.context.respondText(contentType = ContentType.Application.Json, text = cache.getPlaylistCache()[id].toString())
        }
        if (new != null && new != "true" || new != null && new != "false") {
            return call.context.respondText(
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.BadRequest,
                text = "{\"message\": \"Unknown Parameter $new must be either true or false\"}"
            )

        }
        val playlist = backend.getPlaylist(id) ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"No track with that id was found\"}")
        json.append("{ \"playlist\": {\"name\": \"${playlist.name}\", \"owner\": \"${playlist.owner.displayName}\", \"description\": \"${playlist.description}\", \"followers\": ${playlist.followers.total}, \"image\": \"${playlist.images[0].url}\", \"tracks\": [")
        for (i in playlist.tracks) {
            val audioTrack = i.track?.id?.let { backend.getTrack(it) }
            val isComma = if (i.track == playlist.tracks.last()?.track) "" else ","
            json.append("{ \"track\": {\"name\": \"${audioTrack!!.track.name}\", \"artwork\": \"${audioTrack.track.album.images[0].url}\", \"artist\": \"${audioTrack.track.artists[0].name}\", \"popularity\": ${audioTrack.track.popularity}, \"explicit\": ${audioTrack.track.explicit}, \"full_track\": \"${audioTrack.track.name} - ${audioTrack.track.artists[0].name}\", \"features\": {\"dance\": ${audioTrack.features.danceability}, \"energy\": ${audioTrack.features.energy}, \"key\": ${audioTrack.features.key}, \"loud\": ${audioTrack.features.loudness}, \"mode\": ${audioTrack.features.mode}, \"speech\": ${audioTrack.features.speechiness}, \"acoustic\": ${audioTrack.features.acousticness}, \"instrument\": ${audioTrack.features.instrumentalness}, \"live\": ${audioTrack.features.liveness}, \"valence\": ${audioTrack.features.valence}, \"tempo\": ${audioTrack.features.tempo}, \"duration\": ${audioTrack.features.durationMs}}, \"url\": \"https://open.spotify.com/track/${audioTrack.track.id}\"}}$isComma")
        }
        json.append("]}}")
        cache.getPlaylistCache()[id] = json
        print("\r Playlist added to cache ${cache.getPlaylistCache().keys.size} -> ${cache.getPlaylistCache().keys.size+1}")
        call.context.respondText(contentType = ContentType.Application.Json, text = json.toString())
    }
    }
