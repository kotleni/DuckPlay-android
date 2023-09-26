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
class SavedViewModel @Inject constructor(val gamesRepository: GamesRepository, val localGamesRepository: LocalGamesRepository): ViewModel() {
    private val localGames = MutableLiveData<List<Game>>()

    fun getLocalGames(): LiveData<List<Game>> {
        return localGames
    }

    fun loadGames() = CoroutineScope(Dispatchers.Main).launch {
        val games = withContext(Dispatchers.IO) {
            localGamesRepository.getGames()
        }

        this@SavedViewModel.localGames.value = games
    }
}