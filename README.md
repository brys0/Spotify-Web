# Spotify-Web
A simple and easy to use online webserver for parsing spotify tracks playlists albums artists and more!

# Welcome to Spotify Web
### Lets get started!

```kotlin
┌────────────────────────────────────────────Endpoints────────────────────────────────────────────┐
 / | The default home page"))  
 /track | Returns a track json object. Requires a valid track id (/track?id=yourid)"))  
 /playlist | Returns a playlist json object. Requires a valid playlist id (/playlist?id=yourid)
 /album | Returns a album json object. Requires a valid album id (/album?id=yourid)
 /artist | Returns a artist json object. Requires a valid artist id (artist/?id=yourid)
 /new | Returns all converted tracks and new hits on spotify
└────────────────────────────────────────────────────────────────────────────────────────────────┘
