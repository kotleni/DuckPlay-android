package kotleni.duckplay

import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository

class RepositoriesContainer(
    private val gamesRepository: GamesRepository = GamesRepository(),
    private val localGamesRepository: LocalGamesRepository = LocalGamesRepository()
) {
    fun getGamesRepository(): GamesRepository {
        return gamesRepository
    }

    fun getLocalGamesRepository(): LocalGamesRepository {
        return localGamesRepository
    }
}