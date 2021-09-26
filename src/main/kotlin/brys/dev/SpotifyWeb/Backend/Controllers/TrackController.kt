package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.lang.StringBuilder

/**
 * Endpoint for /track
 */
class TrackController(private val spotify: SpotifyWebBackend, private val cache: CacheManager): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        /**
         * If track ID null return an error
         */
         val id = call.context.parameters["id"] ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"Track ID Required\"}")
        /**
         * Parameter to skip cache and get a new track or attempt to find cached track
         */
         val new = call.context.parameters["skipcache"] != null
        /**
         * Backend redeclaration
         */
         val backend = spotify
        /**
         * If id is empty error
         */
         if(id.isEmpty()) {
             return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"Track ID Required\"}")
         }
        /**
         * Requesting a new track
         */
        if (new) {
            @Suppress
            val audioTrack = backend.getTrack(id) ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"No track with that id was found\"}")
            val payload = mapper.writeValueAsString(object {
                val track = audioTrack
            })
            cache.getTrackCache()[id] = payload
            return call.context.respondText(contentType = ContentType.Application.Json, text = payload)
        }
        if (cache.getTrackCache().containsKey(id)) {
            return call.context.respondText(contentType = ContentType.Application.Json, text = cache.getTrackCache()[id].toString())
        }
        if (!new) {
            val cachedTrack = cache.getTrackCache()[id]
            if (cachedTrack == null) {
                val audioTrack = backend.getTrack(id) ?: return call.context.respondText(
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.OK,
                    text = "{\"message\": \"No track with that id was found\"}"
                )
                val payload = mapper.writeValueAsString(object {
                    val track = audioTrack
                })
                cache.getTrackCache()[id] = payload
                call.context.respondText(contentType = ContentType.Application.Json, text = payload)
            } else {
                call.context.respondText(contentType = ContentType.Application.Json, text = cachedTrack)
            }

        }
    }
}