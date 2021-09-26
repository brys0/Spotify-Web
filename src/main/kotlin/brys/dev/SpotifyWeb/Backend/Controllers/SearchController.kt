package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Api.data.AudioTrack
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import brys.dev.SpotifyWeb.Server.Util.Util
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.lang.StringBuilder

class SearchController(private val spotify: SpotifyWebBackend, private val cache: CacheManager): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        /**
         * Track limit INT
         */
        val limit = call.context.parameters["limit"]?.toInt() ?: 5
        /**
         * Track features? BOOLEAN
         */
        val features = call.context.parameters["features"]
        /**
         * Get our query string if null lets send a failed response
         */
        val query = call.context.parameters["q"] ?: return call.context.respondText(
            contentType = ContentType.Application.Json,
            status = HttpStatusCode.BadRequest,
            text = "{\"message\": \"Query is required\"}"
        )
        /**
         * If the int is larger then the limit lets send an error
         */
        if (!Util.Integer.isInt(limit.toString())) {
            return call.context.respondText(
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.BadRequest,
                text = "{\"message\": \"Limit is required to be an integer\"}"
            )
        }
        /**
         * Lets search the tracks
         */
        val data = spotify.searchTracks(query, limit, features != null)
        /**
         * Payload response
         */
        val searchTracks = ArrayList<AudioTrack>()
        searchTracks.addAll(data)
            val payload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object {
                val search = searchTracks
            })
            return call.context.respondText(contentType = ContentType.Application.Json, text = payload)
    }

}