# Spotify-Web
A simple and easy to use online webserver for parsing spotify tracks playlists albums artists and more!

# Welcome to Spotify Web
### Lets get started!

> Woah what port is this hosted on?

The server will be hosted on port `8080` (You can change this! ⬇️ look below ⬇️) on the machine if your also connecting to it on the same machine just put in your browser (or code) `localhost:8080`

```kotlin
┌────────────────────────────────────────────Endpoints────────────────────────────────────────────┐
 / | The default home page
 /track | Returns a track json object. Requires a valid track id (/track?id=yourid)
 /playlist | Returns a playlist json object. Requires a valid playlist id (/playlist?id=yourid)
 /album | Returns a album json object. Requires a valid album id (/album?id=yourid)
 /artist | Returns a artist json object. Requires a valid artist id (artist/?id=yourid)
 /new | Returns all converted tracks and new hits on spotify
└────────────────────────────────────────────────────────────────────────────────────────────────┘
```
> Can I change the database?

Right now currently on this release it only holds data in a HashMap<> or "Cache" effectively, However in the future it should be able to use MongoDB and Redis once we get the bugs figured out!

> Can I change the port of the webserver?

Yes you can! just add `-Dserver.port=YourPort` to your startup looking something like this `java -Dserver.port=9090 -jar ./SpotifyWeb-1.0.0.jar` Will run the default web server on port **9090**

> I need a version for `<Insert_Programming_Lang_Here>` when will that be coming out?

Never, unless someone decides to make a version for other programming languages.

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

