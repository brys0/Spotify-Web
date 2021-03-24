package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import brys.dev.SpotifyWeb.Server.Util.Util
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import java.lang.StringBuilder

class SearchController(private val spotify: SpotifyWebBackend, private val cache: CacheManager): GenericControllerImpl {
    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>) {
        val limit = call.context.parameters["limit"]
        val query = call.context.parameters["q"] ?: return call.context.respondText(
            contentType = ContentType.Application.Json,
            status = HttpStatusCode.BadRequest,
            text = "{\"message\": \"Query is required\"}"
        )
        if (limit == null) {
            val data = spotify.searchTracks(query, 5)
            val json = StringBuilder()
            if (cache.getSearchCache().containsKey(query)) {
                return call.context.respondText(contentType = ContentType.Application.Json, text = cache.getSearchCache()[query].toString())
            }
            json.append("{ \"search\": [")
            for (o in data) {
                val isComma = if (o == data.last()) "" else ","
                json.append("{ \"track\": {\"name\": \"${o?.name}\", \"artwork\": \"${o!!.album.images[0].url}\", \"artist\": \"${o?.artists!![0].name}\", \"popularity\": ${o?.popularity}, \"explicit\": ${o?.explicit}, \"full_track\": \"${o?.name} - ${o?.artists!![0].name}\", \"url\": \"https://open.spotify.com/track/${o.id}\"}}$isComma")
            }
            json.append("]}")
            cache.getSearchCache()[query] = json
            print("\r Search added to cache ${cache.getSearchCache().keys.size} -> ${cache.getSearchCache().keys.size+1}")
            return call.context.respondText(contentType = ContentType.Application.Json, text = json.toString())
        }
        if (!Util.Integer.isInt(limit.toString())) {
            return call.context.respondText(
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.BadRequest,
                text = "{\"message\": \"Limit is required to be an integer\"}"
            )
        }
        val amount = limit.toInt()
        if (amount > 50 )  return call.context.respondText(contentType = ContentType.Application.Json, status = HttpStatusCode.InsufficientStorage, text = "{\"message\": \"Limit cant't be over 50\"}")
        val data = spotify.searchTracks(query, amount)
        val json = StringBuilder()
        json.append("{ \"search\": [")
        for (o in data) {
            val isComma = if (o == data.last()) "" else ","
            json.append("{ \"track\": {\"name\": \"${o?.name}\", \"artwork\": \"${o!!.album.images[0].url}\", \"artist\": \"${o?.artists!![0].name}\", \"popularity\": ${o?.popularity}, \"explicit\": ${o?.explicit}, \"full_track\": \"${o?.name} - ${o?.artists!![0].name}\", \"url\": \"https://open.spotify.com/track/${o.id}\"}}$isComma")
        }
        json.append("]}")
        cache.getSearchCache()[query] = json
        print("\r Search added to cache ${cache.getSearchCache().keys.size} -> ${cache.getSearchCache().keys.size+1}")
        return call.context.respondText(contentType = ContentType.Application.Json, text = json.toString())
    }
}