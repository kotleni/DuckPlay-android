package kotleni.duckplay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideGamesRepository(
    ): GamesRepository {
        return GamesRepository()
    }

    @Singleton
    @Provides
    fun provideLocalGamesRepository(
    ): LocalGamesRepository {
        return LocalGamesRepository()
    }
}