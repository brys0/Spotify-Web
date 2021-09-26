package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.util.*

class HomeController: GenericControllerImpl {
    private val home =
        Scanner(javaClass.getResourceAsStream("/index.html")!!, "UTF-8").useDelimiter("\\A").next()
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        call.context.respondText(contentType = ContentType.Text.Html, status = HttpStatusCode.Accepted, text = home)
    }
}