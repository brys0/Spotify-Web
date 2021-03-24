package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.io.File

class HomeController: GenericControllerImpl {
    private val home = File("./pages/index.html").readText()
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>) {
        call.context.respondText(contentType = ContentType.Text.Html, status = HttpStatusCode.Accepted, text = home)
    }
}