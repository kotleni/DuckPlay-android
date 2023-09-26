package kotleni.duckplay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotleni.duckplay.RepositoriesContainer
import kotleni.duckplay.entities.Game
import kotleni.duckplay.entities.GameInfo
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(val gamesRepository: GamesRepository, val localGamesRepository: LocalGamesRepository): ViewModel() {
    private val gameInfo = MutableLiveData<GameInfo>()
    private val localGame = MutableLiveData<Game>()

    fun getGameInfo(): LiveData<GameInfo> {
        return gameInfo
    }

    fun getLocalGame(): LiveData<Game> {
        return localGame
    }

    fun loadGame(id: String) = CoroutineScope(Dispatchers.Main).launch {
        val gameInfo = withContext(Dispatchers.IO) {
           gamesRepository.getGameInfo(id)
        }

        this@GameViewModel.gameInfo.value = gameInfo
    }

    fun loadOfflineGame(id: String) = CoroutineScope(Dispatchers.Main).launch {
        val games = withContext(Dispatchers.IO) {
           localGamesRepository.getGames()
        }

        games.forEach {
            if(it.id == id)
                this@GameViewModel.localGame.value = it
        }
    }
}