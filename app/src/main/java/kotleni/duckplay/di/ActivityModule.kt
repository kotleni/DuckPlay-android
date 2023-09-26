package kotleni.duckplay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import kotleni.duckplay.viewmodels.GameViewModel

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideGameViewModel(
        gamesRepository: GamesRepository,
        localGamesRepository: LocalGamesRepository) : GameViewModel {
        return GameViewModel(gamesRepository, localGamesRepository)
    }
}