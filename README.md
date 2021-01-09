# Spotify-Web
![img](https://raw.githubusercontent.com/brys0/Spotify-Web/master/Art/sws-spotify.png)
A simple and easy to use online webserver for parsing spotify tracks playlists albums artists and more!

# Welcome to Spotify Web
### Lets get started!

> Woah what port is this hosted on?

The server will be hosted on port `8080` (You can change this! ⬇️ look below ⬇️) on the machine if your also connecting to it on the same machine just put in your browser (or code) `localhost:8080`

```kotlin
┌────────────────────────────────────────────────Endpoints────────────────────────────────────────────────┐
 /                   | The default home page
 /track              | Returns a track json object. Requires a valid track id (/track?id=yourid)
 /playlist           | Returns a playlist json object. Requires a valid playlist id (/playlist?id=yourid)
 /album              | Returns a album json object. Requires a valid album id (/album?id=yourid)
 /artist             | Returns a artist json object. Requires a valid artist id (artist/?id=yourid)
 /new                | Returns all converted tracks and new hits on spotify
 /categories         | Get all categories and their names / images
 /user               | Returns a user json object. Requires a valid username (/user?name=YourUsername)
 /system/cpu         | Returns cpu thread stats
 /system/mem         | Returns memory stats
 /system/gc          | Calls the jvm to attempting to garbage collect
 /system/cache       | Returns cache stats
 /system/cache/clear | Clears all cache
└────────────────────────────────────────────────────────────────────────────────────────────────────────┘
```
> Can I change the database?

Right now currently on this release it only holds data in a HashMap<> or "Cache" effectively, However in the future it should be able to use MongoDB and Redis once we get the bugs figured out!

> Can I change the port of the webserver?

Yes you can! just add `-Dserver.port=YourPort` to your startup looking something like this `java -Dserver.port=9090 -jar ./SpotifyWeb-1.0.0.jar` Will run the default web server on port **9090**

> I need a version for `<Insert_Programming_Lang_Here>` when will that be coming out?

Never, unless someone decides to make a version for other programming languages.
### Community Clients

[**NodeJS/TS** `SWWrap`](https://github.com/WubzyGD/SWWrap) - [**WubzyGD**](https://github.com/WubzyGD)
> Will there ever be a lighter version of this webserver available?

Yes! There should be a version coming out that just uses the raw JRE suppport and should shrink the memory usage and jar size by up to 50%

> How can I contribute?

1. Fork this repo
2. Update whatever you were planning
3. Submit a PR with the tag **UPDATE**
4. Wait.

Make sure to read the contributing guidelines too! [`Click Here`](https://github.com/brys0/Spotify-Web/blob/master/CONTRIBUTING.md)
> What does each request look like?

**Track**
```json
{
	"name": String,
	"artwork": String,
	"artist": String,
	"popularity": Int,
	"explicit": Boolean,
	"duration": Long,
	"track_num": Int,
	"converted_trk": String
}
```

**Playlist**
```json
{
	"name": String,
	"owner": String,
	"description": String,
	"followers": Int,
	"image": String,
	"snapshot": String,
	"tracks": Array
}
```
**Album**
```json
{
	"name": String,
	"artists": String,
	"release_date": String,
	"popularity": Int,
	"image": String,
	"label": String,
	"total_tracks": Int,
	"tracks": Array
}
```
**Artist**
```json
{
	"name": String,
	"followers": Int,
	"genres": Array,
	"image": String,
	"top_tracks": Array
}
```

**New**
```json
{
	"Track_Rec": Array
}
```

**User**
```json
{
"name": String,
"followers": Int,
"image": String
}
```
**System/CPU**
```json
{
"alive": Int,
"parked": Int
}
```
**System/MEM**
```json
{
"total": String,
"max": String,
"available": String,
"used": String
}
```
**System/GC**
```json
{
"gc": Boolean
}
```
**System/CACHE**
```json
{
"tracks": Int,
"playlists": Int,
"albums": Int
}
```
**System/CACHE/CLEAR**
```json
{
"cache": Boolean
}
```
