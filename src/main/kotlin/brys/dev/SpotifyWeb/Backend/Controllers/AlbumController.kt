package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.util.pipeline.*

class AlbumController(private val spotify: SpotifyWebBackend, private val cache: CacheManager): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {

        TODO("Not yet implemented")
    }
}