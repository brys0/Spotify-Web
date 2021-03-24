package brys.dev.SpotifyWeb.Backend.Controllers.admin

import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

class CacheController(private val cache: CacheManager, private val key: String): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>) {
        val auth = call.context.parameters["auth"]
        val reset = call.context.parameters["reset"]
        if (auth == null || auth != key) {
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.Forbidden, text = "{\"message\": \"Invaild Auth\"}")
        }
        if (reset != null && reset == "true") {
            cache.resetCache()
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"message\": \"Cache has been cleared.\"}")
        }
        if (reset == null || reset == "false") {
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"tracks\": ${cache.getTrackCache().size}, \"playlists\": ${cache.getPlaylistCache().size}}")
        }
    }
}