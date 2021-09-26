package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Api.data.AbstractedAlbum
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

class NewTracksController(private val spotify: SpotifyWebBackend): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        try {
            val limit =
                if (runCatching { call.context.parameters["limit"]?.toInt() }.isFailure) 5 else call.context.parameters["limit"]?.toInt()
            val albums = spotify.app.browse.getNewReleases(limit).items
            val abstractedAlbums = ArrayList<AbstractedAlbum>()
            for (album in albums) {
                abstractedAlbums.add(AbstractedAlbum(album.href, album.id, album.images.first().url, album.name, album.type, null, "${album.releaseDate.year}-${album.releaseDate.month}-${album.releaseDate.day}", album.totalTracks,  album.albumGroup?.name))
            }
            return call.context.respondText(
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.Found,
                text = mapper.writeValueAsString(object {
                    val new = abstractedAlbums
                })
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}