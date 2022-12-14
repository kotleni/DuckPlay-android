package kotleni.duckplay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotleni.duckplay.RepositoriesContainer
import kotleni.duckplay.entities.Game
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GamesViewModel(val repositoriesContainer: RepositoriesContainer): ViewModel() {
    private val games = MutableLiveData<List<Game>>()
    private val localGames = MutableLiveData<List<Game>>()

    fun getGames(): LiveData<List<Game>> {
        return games
    }

    fun getLocalGames(): LiveData<List<Game>> {
        return localGames
    }

    fun loadGames() = CoroutineScope(Dispatchers.Main).launch {
        val games = withContext(Dispatchers.IO) {
            repositoriesContainer.getGamesRepository().getGames()
        }

        val localGames = withContext(Dispatchers.IO) {
            repositoriesContainer.getLocalGamesRepository().getGames()
        }

        if(games != null) {
            this@GamesViewModel.games.value = games
            this@GamesViewModel.localGames.value = localGames
        }
    }

    fun downloadGame(game: Game) = CoroutineScope(Dispatchers.Main).launch {
        val game = withContext(Dispatchers.IO) {
            repositoriesContainer.getLocalGamesRepository().downloadGame(game)
        }

        loadGames()
    }

    fun removeSavedGame(game: Game) = CoroutineScope(Dispatchers.Main).launch {
        withContext(Dispatchers.IO) {
            repositoriesContainer.getLocalGamesRepository().removeGame(game)
        }

        loadGames()
    }
}

//class GamesViewModelProviderFactory(val gamesRepository: GamesRepository, val localGamesRepository: LocalGamesRepository): ViewModelProvider.Factory {
//    override fun <T: ViewModel> create(modelClass: Class<T>): T {
//        return GamesViewModel(gamesRepository, localGamesRepository) as T
//    }
//}