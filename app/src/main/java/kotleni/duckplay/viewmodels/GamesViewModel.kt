package kotleni.duckplay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotleni.duckplay.RepositoriesContainer
import kotleni.duckplay.entities.Game
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(val gamesRepository: GamesRepository, val localGamesRepository: LocalGamesRepository): ViewModel() {
    enum class UIState { IDLE, LOADING, DOWNLOADING, REMOVING }

    private val games = MutableLiveData<List<Game>>()
    private val localGames = MutableLiveData<List<Game>>()

    private val _uiState = MutableLiveData<UIState>(UIState.IDLE)
    val uiState: LiveData<UIState> = _uiState

    fun getGames(): LiveData<List<Game>> {
        return games
    }

    fun getLocalGames(): LiveData<List<Game>> {
        return localGames
    }

    fun loadGames() = CoroutineScope(Dispatchers.Main).launch {
        _uiState.value = UIState.LOADING
        val games = withContext(Dispatchers.IO) {
            gamesRepository.getGames()
        }

        val localGames = withContext(Dispatchers.IO) {
            localGamesRepository.getGames()
        }

        if(games != null) {
            this@GamesViewModel.games.value = games
            this@GamesViewModel.localGames.value = localGames
        }
        _uiState.value = UIState.IDLE
    }

    fun downloadGame(game: Game) = CoroutineScope(Dispatchers.Main).launch {
        _uiState.value = UIState.DOWNLOADING
        val game = withContext(Dispatchers.IO) {
            localGamesRepository.downloadGame(game)
        }
        loadGames()
    }

    fun removeSavedGame(game: Game) = CoroutineScope(Dispatchers.Main).launch {
        _uiState.value = UIState.REMOVING
        withContext(Dispatchers.IO) {
            localGamesRepository.removeGame(game)
        }
        loadGames()
    }
}