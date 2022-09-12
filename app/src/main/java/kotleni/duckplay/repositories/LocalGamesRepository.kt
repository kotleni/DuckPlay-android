package kotleni.duckplay.repositories

import com.google.gson.Gson
import kotleni.duckplay.download
import kotleni.duckplay.entities.Game
import kotleni.duckplay.network.DuckplayAPI
import java.io.File

class LocalGamesRepository {
    private val dirPath = "/data/data/kotleni.duckplay/files/games7/"
    private val gson = Gson()

    init {
        val dirFile = File(dirPath)
        if(!dirFile.exists()) {
            dirFile.mkdirs()
        }
    }
    fun getGames(): List<Game> {
        val games: ArrayList<Game> = arrayListOf()
        val dirs = File(dirPath).listFiles()
        dirs.forEach {
            // println("game dir for loading: ${it!!.path}")
            val jsonFile = File("${it!!.path}/game.json")
            val content = jsonFile.readText()
            val game = gson.fromJson(content, Game::class.java)
            games.add(game)
        }

        return games
    }

    fun downloadGame(game: Game): Game {
        val webUrl = game.getWebUrl()
        val info = DuckplayAPI.getService().getGameInfo(game.id).execute()
        File("${dirPath}/${game.id}/")
            .mkdirs()
        info.body()?.let {
            it.files.forEach {
                println("download ${webUrl}/$it")
                download("${webUrl}/$it", "${dirPath}/${game.id}/$it")
            }
        }
        val json = gson.toJson(game)
        val jsonFile = File("${dirPath}/${game.id}/game.json")
        jsonFile.writeText(json)

        return game
    }

    fun removeGame(game: Game) {
        File("${dirPath}/${game.id}/")
            .deleteRecursively()
    }
}