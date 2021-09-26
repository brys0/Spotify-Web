package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.util.pipeline.*

class CategoryController(private val spotify: SpotifyWebBackend): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        spotify.app.browse.getCategory("")
    }
}