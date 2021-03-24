package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.lang.StringBuilder


class TrackController(private val spotify: SpotifyWebBackend, private val cache: CacheManager): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>) {
         val json = StringBuilder()
         val id = call.context.parameters["id"] ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"Track ID Required\"}")
         val new = call.context.parameters["skipcache"]
         val backend = spotify
         if(id.isEmpty()) {
             return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"Track ID Required\"}")
         }
        if (new == "true") {
            val audioTrack = backend.getTrack(id) ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"No track with that id was found\"}")
            json.append("{ \"track\": {\"name\": \"${audioTrack.track.name}\", \"artwork\": \"${audioTrack.track.album.images[0].url}\", \"artist\": \"${audioTrack.track.artists[0].name}\", \"popularity\": ${audioTrack.track.popularity}, \"explicit\": ${audioTrack.track.explicit}, \"full_track\": \"${audioTrack.track.name} - ${audioTrack.track.artists[0].name}\", \"features\": {\"dance\": ${audioTrack.features.danceability}, \"energy\": ${audioTrack.features.energy}, \"key\": ${audioTrack.features.key}, \"loud\": ${audioTrack.features.loudness}, \"mode\": ${audioTrack.features.mode}, \"speech\": ${audioTrack.features.speechiness}, \"acoustic\": ${audioTrack.features.acousticness}, \"instrument\": ${audioTrack.features.instrumentalness}, \"live\": ${audioTrack.features.liveness}, \"valence\": ${audioTrack.features.valence}, \"tempo\": ${audioTrack.features.tempo}, \"duration\": ${audioTrack.features.durationMs}}, \"url\": \"https://open.spotify.com/track/${audioTrack.track.id}\"}}")
            return call.context.respondText(contentType = ContentType.Application.Json, text = json.toString())
        }
        if (cache.getTrackCache().containsKey(id)) {
            return call.context.respondText(contentType = ContentType.Application.Json, text = cache.getTrackCache()[id].toString())
        }
         if (new != null && new != "true" || new != null && new != "false") {
             return call.context.respondText(
                 contentType = ContentType.Application.Json,
                 status = HttpStatusCode.OK,
                 text = "{\"message\": \"Unknown Parameter $new must be either true or false\"}"
             )

         }
          val audioTrack = backend.getTrack(id) ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"No track with that id was found\"}")
          json.append("{ \"track\": {\"name\": \"${audioTrack.track.name}\", \"artwork\": \"${audioTrack.track.album.images[0].url}\", \"artist\": \"${audioTrack.track.artists[0].name}\", \"popularity\": ${audioTrack.track.popularity}, \"explicit\": ${audioTrack.track.explicit}, \"full_track\": \"${audioTrack.track.name} - ${audioTrack.track.artists[0].name}\", \"features\": {\"dance\": ${audioTrack.features.danceability}, \"energy\": ${audioTrack.features.energy}, \"key\": ${audioTrack.features.key}, \"loud\": ${audioTrack.features.loudness}, \"mode\": ${audioTrack.features.mode}, \"speech\": ${audioTrack.features.speechiness}, \"acoustic\": ${audioTrack.features.acousticness}, \"instrument\": ${audioTrack.features.instrumentalness}, \"live\": ${audioTrack.features.liveness}, \"valence\": ${audioTrack.features.valence}, \"tempo\": ${audioTrack.features.tempo}, \"duration\": ${audioTrack.features.durationMs}}, \"url\": \"https://open.spotify.com/track/${audioTrack.track.id}\"}}")
        cache.getTrackCache()[id] = json
        print("\r Track added to cache ${cache.getTrackCache().keys.size} -> ${cache.getTrackCache().keys.size+1}")
        call.context.respondText(contentType = ContentType.Application.Json, text = json.toString())
    }
}