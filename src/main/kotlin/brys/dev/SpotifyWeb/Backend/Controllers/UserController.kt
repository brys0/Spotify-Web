package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

/**
 * Endpoint for /user
 */
class UserController(private val spotify: SpotifyWebBackend, private val cache: CacheManager): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        val id = call.context.parameters["id"] ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.BadRequest, text = "{\"message\": \"User ID Required\"}")
        val grabPlaylists = call.context.parameters["getPlaylists"].isNullOrEmpty()
        val user = spotify.getUser(id) ?: return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.NotFound, text = "{\"message\": \"No user with that id was found\"}")
        if (grabPlaylists) {
            /**
             * We don't want to grab more than the first 10 playlists.. this can be very demanding on the api
             */
            val playlists = spotify.app.playlists.getUserPlaylists(id, 10).items
            val payload = mapper.writeValueAsString(object {
                val user = user
                val playlists = playlists
            })
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.Found, text = payload)
        } else {
            val payload = mapper.writeValueAsString(object {
                val user = user
            })
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.Found, text = payload)
        }
    }
}