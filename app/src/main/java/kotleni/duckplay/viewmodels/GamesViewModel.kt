package kotleni.duckplay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotleni.duckplay.entities.Game
import kotleni.duckplay.repositories.GamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GamesViewModel(val gamesRepository: GamesRepository): ViewModel() {
    private val games = MutableLiveData<List<Game>>()

    fun getGames(): LiveData<List<Game>> {
        return games
    }

    fun loadGames() = CoroutineScope(Dispatchers.Main).launch {
        val games = withContext(Dispatchers.IO) {
            gamesRepository.getGames()
        }

        if(games != null) {
            this@GamesViewModel.games.value = games
        }
    }
}

class GamesViewModelProviderFactory(val gamesRepository: GamesRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return GamesViewModel(gamesRepository) as T
    }
}