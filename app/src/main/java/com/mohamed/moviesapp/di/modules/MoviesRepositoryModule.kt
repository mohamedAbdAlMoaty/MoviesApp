package com.mohamed.moviesapp.di.modules

import com.mohamed.moviesapp.persistence.MovieDao
import com.mohamed.moviesapp.remote.MovieInterface
import com.mohamed.moviesapp.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class MoviesRepositoryModule {

    @Provides
    @ActivityRetainedScoped    // @ActivityRetainedScoped  mean it will be live as long as viewModel live
    fun provideMoviesRepository(movieInterface: MovieInterface,movieDao: MovieDao): MoviesRepository {
        return MoviesRepository(movieInterface,movieDao)
    }

}