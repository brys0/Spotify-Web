package brys.dev.SpotifyWeb.Backend.Controllers

import brys.dev.SpotifyWeb.Backend.Api.SpotifyWebBackend
import brys.dev.SpotifyWeb.Backend.Api.data.AudioTrack
import brys.dev.SpotifyWeb.Backend.Api.data.AudioTrackAcceptableAsJSON
import brys.dev.SpotifyWeb.Backend.Api.data.PlaylistAudioTrack
import brys.dev.SpotifyWeb.Backend.Api.data.PlaylistWithInternalTracks
import brys.dev.SpotifyWeb.Backend.Cache.CacheManager
import brys.dev.SpotifyWeb.Backend.Controllers.types.GenericControllerImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

/**
 * Endpoint for /playlist
 */
class PlaylistController(private val spotify: SpotifyWebBackend, private val cache: CacheManager) :
    GenericControllerImpl {

    override suspend fun response(call: PipelineContext<Unit, ApplicationCall>, mapper: ObjectMapper) {
        try {
            val id = call.context.parameters["id"] ?: return call.context.respondText(
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.BadRequest,
                text = "{\"message\": \"Playlist ID Required\"}"
            )
            val new = call.context.parameters["skipcache"].isNullOrEmpty()
            val getFeatures = call.context.parameters["features"].isNullOrEmpty()

            if (new) {
                val playlist = spotify.getPlaylist(id) ?: return call.context.respondText(
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.NotFound,
                    text = "{\"message\": \"Playlist with that id was not found\"}"
                )
                val tracks = ArrayList<PlaylistAudioTrack>()
                if (getFeatures) {
                    for (track in playlist.tracks) {
                        val features = track.track?.id?.let { spotify.app.tracks.getAudioFeatures(it) }!!
                        tracks.add(
                            PlaylistAudioTrack
                                (
                                track = AudioTrack( AudioTrackAcceptableAsJSON(
                                    track.track?.href!!,
                                    track.track?.id!!,
                                    track.track?.uri!!,
                                    track.track?.asTrack?.isPlayable!!,
                                    track.track?.asTrack?.discNumber!!,
                                    track.track?.asTrack?.durationMs!!,
                                    track.track?.asTrack?.explicit!!,
                                    track.track?.asTrack?.name!!,
                                    track.track?.asTrack?.popularity!!,
                                    track.track?.asTrack?.previewUrl,
                                    track.track?.asTrack?.trackNumber!!,
                                    track.track?.asTrack?.type!!,
                                    track.track?.asTrack?.episode!!,
                                    track.track?.asTrack?.isLocal!!
                                ), null),
                                features = features,
                                addedAt = track.addedAt,
                                addedBy = track.addedBy,
                                thumb = track.videoThumbnail?.url,
                                primaryColor = track.primaryColor
                            )
                        )
                    }

                    val payload = mapper.writeValueAsString(object {
                        val playlist = PlaylistWithInternalTracks(
                            playlist.href,
                            playlist.id,
                            playlist.uri,
                            playlist.collaborative,
                            playlist.description,
                            playlist.followers,
                            playlist.primaryColor,
                            playlist.images,
                            playlist.name,
                            tracks,
                            playlist.owner,
                            playlist.public,
                            playlist.type
                        )
                    })
                    cache.getPlaylistCache()[id] = payload
                    return call.context.respondText(
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.Found,
                        text = payload
                    )
                } else {
                    for (track in playlist.tracks) {
                        tracks.add(
                            PlaylistAudioTrack
                                (
                                track = AudioTrack( AudioTrackAcceptableAsJSON(
                                    track.track?.href!!,
                                    track.track?.id!!,
                                    track.track?.uri!!,
                                    track.track?.asTrack?.isPlayable!!,
                                    track.track?.asTrack?.discNumber!!,
                                    track.track?.asTrack?.durationMs!!,
                                    track.track?.asTrack?.explicit!!,
                                    track.track?.asTrack?.name!!,
                                    track.track?.asTrack?.popularity!!,
                                    track.track?.asTrack?.previewUrl,
                                    track.track?.asTrack?.trackNumber!!,
                                    track.track?.asTrack?.type!!,
                                    track.track?.asTrack?.episode!!,
                                    track.track?.asTrack?.isLocal!!
                                ), null),
                                null,
                                addedAt = track.addedAt,
                                addedBy = track.addedBy,
                                thumb = track.videoThumbnail?.url,
                                primaryColor = track.primaryColor
                            )
                        )
                    }
                    val payload = mapper.writeValueAsString(object {
                        val playlist = PlaylistWithInternalTracks(
                            playlist.href,
                            playlist.id,
                            playlist.uri,
                            playlist.collaborative,
                            playlist.description,
                            playlist.followers,
                            playlist.primaryColor,
                            playlist.images,
                            playlist.name,
                            tracks,
                            playlist.owner,
                            playlist.public,
                            playlist.type
                        )
                    })
                    return call.context.respondText(
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.Found,
                        text = payload
                    )
                }
            } else {

                val tracks = ArrayList<PlaylistAudioTrack>()
                if (cache.getPlaylistCache().containsKey(id)) {
                    val playlistData = cache.getPlaylistCache()[id]
                    if (playlistData?.contains("\"features\": null") == true && getFeatures) {
                        val playlist = spotify.getPlaylist(id) ?: return call.context.respondText(
                            contentType = ContentType.Application.Json,
                            status = HttpStatusCode.NotFound,
                            text = "{\"message\": \"Playlist with that id was not found\"}"
                        )
                        for (track in playlist.tracks) {
                            val features = track.track?.id?.let { spotify.app.tracks.getAudioFeatures(it) }!!
                            tracks.add(
                                PlaylistAudioTrack
                                    (
                                    track = AudioTrack( AudioTrackAcceptableAsJSON(
                                        track.track?.href!!,
                                        track.track?.id!!,
                                        track.track?.uri!!,
                                        track.track?.asTrack?.isPlayable!!,
                                        track.track?.asTrack?.discNumber!!,
                                        track.track?.asTrack?.durationMs!!,
                                        track.track?.asTrack?.explicit!!,
                                        track.track?.asTrack?.name!!,
                                        track.track?.asTrack?.popularity!!,
                                        track.track?.asTrack?.previewUrl,
                                        track.track?.asTrack?.trackNumber!!,
                                        track.track?.asTrack?.type!!,
                                        track.track?.asTrack?.episode!!,
                                        track.track?.asTrack?.isLocal!!
                                    ), null),
                                    features = features,
                                    addedAt = track.addedAt,
                                    addedBy = track.addedBy,
                                    thumb = track.videoThumbnail?.url,
                                    primaryColor = track.primaryColor
                                )
                            )
                        }
                        val payload = mapper.writeValueAsString(object {
                            val playlist = PlaylistWithInternalTracks(
                                playlist.href,
                                playlist.id,
                                playlist.uri,
                                playlist.collaborative,
                                playlist.description,
                                playlist.followers,
                                playlist.primaryColor,
                                playlist.images,
                                playlist.name,
                                tracks,
                                playlist.owner,
                                playlist.public,
                                playlist.type
                            )
                        })
                        cache.getPlaylistCache()[id] = payload
                        return call.context.respondText(
                            contentType = ContentType.Application.Json,
                            status = HttpStatusCode.Found,
                            text = payload
                        )
                    } else {
                        return call.context.respondText(
                            contentType = ContentType.Application.Json,
                            status = HttpStatusCode.Found,
                            text = cache.getPlaylistCache()[id]!!
                        )
                    }
                }
            }
            val playlist = spotify.getPlaylist(id) ?: return call.context.respondText(
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.NotFound,
                text = "{\"message\": \"Playlist with that id was not found\"}"
            )
            val tracks = ArrayList<PlaylistAudioTrack>()
            for (track in playlist.tracks) {
                tracks.add(
                    PlaylistAudioTrack
                        (
                        track = AudioTrack( AudioTrackAcceptableAsJSON(
                            track.track?.href!!,
                            track.track?.id!!,
                            track.track?.uri!!,
                            track.track?.asTrack?.isPlayable!!,
                            track.track?.asTrack?.discNumber!!,
                            track.track?.asTrack?.durationMs!!,
                            track.track?.asTrack?.explicit!!,
                            track.track?.asTrack?.name!!,
                            track.track?.asTrack?.popularity!!,
                            track.track?.asTrack?.previewUrl,
                            track.track?.asTrack?.trackNumber!!,
                            track.track?.asTrack?.type!!,
                            track.track?.asTrack?.episode!!,
                            track.track?.asTrack?.isLocal!!
                        ), null),
                        null,
                        addedAt = track.addedAt,
                        addedBy = track.addedBy,
                        thumb = track.videoThumbnail?.url,
                        primaryColor = track.primaryColor
                    )
                )
            }
            val payload = mapper.writeValueAsString(object {
                val playlist = PlaylistWithInternalTracks(
                    playlist.href,
                    playlist.id,
                    playlist.uri,
                    playlist.collaborative,
                    playlist.description,
                    playlist.followers,
                    playlist.primaryColor,
                    playlist.images,
                    playlist.name,
                    tracks,
                    playlist.owner,
                    playlist.public,
                    playlist.type
                )
            })
            cache.getPlaylistCache()[id] = payload
            return call.context.respondText(
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.Found,
                text = payload
            )
         } catch (e: Exception) {
             e.printStackTrace()
         }
    }
}
