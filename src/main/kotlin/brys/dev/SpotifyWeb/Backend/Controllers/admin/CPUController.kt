package brys.dev.SpotifyWeb.Backend.Controllers.admin

import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.util.pipeline.*

class CPUController: GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        TODO("Not yet implemented")
    }
}