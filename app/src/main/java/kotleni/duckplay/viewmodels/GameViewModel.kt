package kotleni.duckplay.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotleni.duckplay.entities.GameInfo
import kotleni.duckplay.repositories.GamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel(val gamesRepository: GamesRepository): ViewModel() {
    private val gameInfo = MutableLiveData<GameInfo>()

    fun getGameInfo(): LiveData<GameInfo> {
        return gameInfo
    }

    fun loadGame(id: String) = CoroutineScope(Dispatchers.Main).launch {
        val gameInfo = withContext(Dispatchers.IO) {
            gamesRepository.getGameInfo(id)
        }

        this@GameViewModel.gameInfo.value = gameInfo
    }
}

class GameViewModelProviderFactory(val gamesRepository: GamesRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(gamesRepository) as T
    }
}