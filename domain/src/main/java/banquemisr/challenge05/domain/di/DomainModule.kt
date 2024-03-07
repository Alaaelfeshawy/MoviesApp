package banquemisr.challenge05.domain.di

import banquemisr.challenge05.domain.usecase.getmoviedetails.GetMovieDetailsUseCase
import banquemisr.challenge05.domain.usecase.getmoviedetails.IGetMovieDetailsUseCase
import banquemisr.challenge05.domain.usecase.getplayingmovies.GetPlayingMoviesUseCase
import banquemisr.challenge05.domain.usecase.getplayingmovies.IGetPlayingMoviesUseCase
import banquemisr.challenge05.domain.usecase.getpopularmovies.GetPopularMoviesUseCase
import banquemisr.challenge05.domain.usecase.getpopularmovies.IGetPopularMoviesUseCase
import banquemisr.challenge05.domain.usecase.getupcomingmovies.GetUpcomingMoviesUseCase
import banquemisr.challenge05.domain.usecase.getupcomingmovies.IGetUpcomingMoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetPlayingMoviesUseCase(getPlayingMoviesUseCase: GetPlayingMoviesUseCase): IGetPlayingMoviesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetPopularMoviesUseCase(getPopularMoviesUseCase: GetPopularMoviesUseCase): IGetPopularMoviesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetUpcomingMoviesUseCase(getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase): IGetUpcomingMoviesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetMovieDetailsUseCase(getMovieDetailsUseCase: GetMovieDetailsUseCase): IGetMovieDetailsUseCase

}