package kotleni.duckplay.repositories

import kotleni.duckplay.entities.Game
import kotleni.duckplay.network.DuckplayAPI

class GamesRepository {
    fun getGames(): List<Game>? {
        val call = DuckplayAPI.getService().getGames()
        val resp = call.execute()
        val body = resp.body()

        println("GamesRepository::getGames, body: ${body}, ${resp.message()}")

        return body
    }
}