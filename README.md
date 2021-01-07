# Spotify-Web
A simple and easy to use online webserver for parsing spotify tracks playlists albums artists and more!

# Welcome to Spotify Web
### Lets get started!

> Woah what port is this hosted on?

The server for now will always be hosted on port `8080` on the machine if your also connecting to it on the same machine just put in your browser (or code) `localhost:8080`

```kotlin
┌────────────────────────────────────────────Endpoints────────────────────────────────────────────┐
 / | The default home page"))  
 /track | Returns a track json object. Requires a valid track id (/track?id=yourid)"))  
 /playlist | Returns a playlist json object. Requires a valid playlist id (/playlist?id=yourid)
 /album | Returns a album json object. Requires a valid album id (/album?id=yourid)
 /artist | Returns a artist json object. Requires a valid artist id (artist/?id=yourid)
 /new | Returns all converted tracks and new hits on spotify
└────────────────────────────────────────────────────────────────────────────────────────────────┘
