package brys.dev.SpotifyWeb.Backend.Controllers.types

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.util.pipeline.*


interface GenericControllerImpl {
    suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper)
}