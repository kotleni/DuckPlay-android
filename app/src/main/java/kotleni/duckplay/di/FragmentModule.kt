package kotleni.duckplay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import kotleni.duckplay.viewmodels.GamesViewModel
import kotleni.duckplay.viewmodels.SavedViewModel

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {
    @Provides
    fun provideGamesViewModel(
        gamesRepository: GamesRepository,
        localGamesRepository: LocalGamesRepository
    ): GamesViewModel {
        return GamesViewModel(gamesRepository, localGamesRepository)
    }

    @Provides
    fun provideSavedViewModel(
        gamesRepository: GamesRepository,
        localGamesRepository: LocalGamesRepository
    ): SavedViewModel {
        return SavedViewModel(gamesRepository, localGamesRepository)
    }
}