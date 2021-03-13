package com.brys.dev.lib.util.models

import com.adamratzman.spotify.models.*

data class SearchRequest(val playlist: List<SimpleAlbum?>?, val track: List<Track?>?, val artist: List<Artist?>?, val playlists: List<SimplePlaylist?>? )
