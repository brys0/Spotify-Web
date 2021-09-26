package brys.dev.SpotifyWeb.Backend.Controllers.admin

import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import brys.dev.SpotifyWeb.Server.Util.Util
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

class MemoryController(private val key: String): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        val auth = call.context.parameters["auth"]
        val gc = call.context.parameters["gc"]
        if (auth == null && gc == null || auth == key && gc == null) {
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.OK, text = "{\"max\": \"${Util.Memory.max}\", \"total\": \"${Util.Memory.total}\", \"available\": \"${Util.Memory.available}\", \"used\": \"${Util.Memory.used}\" }")
        }
        if (auth == null && gc == "true" || auth != key && gc == "true") {
            return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.Forbidden, text = "{\"message\": \"Invaild Auth\"}")
        }
         if (gc == "true" && auth == key) {
             System.gc()
             return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.Forbidden, text = "{\"message\": \"Garbage Collection Performed\"}")
         }
    }
}