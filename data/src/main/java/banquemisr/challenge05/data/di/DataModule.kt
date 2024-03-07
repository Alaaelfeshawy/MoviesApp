package banquemisr.challenge05.data.di

import banquemisr.challenge05.data.repository.MovieDetailsRepository
import banquemisr.challenge05.data.repository.MoviesRepository
import banquemisr.challenge05.domain.repository.details.IMovieDetailsRepository
import banquemisr.challenge05.domain.repository.movies.IMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsMoviesRepository(moviesRepository: MoviesRepository): IMoviesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsMovieDetailsRepository(movieDetailsRepository: MovieDetailsRepository): IMovieDetailsRepository

}