package kotleni.duckplay.repositories

import kotleni.duckplay.entities.Game
import kotleni.duckplay.entities.GameInfo
import kotleni.duckplay.network.DuckplayAPI

class GamesRepository {
    fun getGames(): List<Game>? {
        val call = DuckplayAPI.getService().getGames()
        val resp = call.execute()
        val body = resp.body()

        return body
    }

    fun getGameInfo(id: String): GameInfo? {
        val call = DuckplayAPI.getService().getGameInfo(id)
        val resp = call.execute()
        val body = resp.body()

        return body
    }
}