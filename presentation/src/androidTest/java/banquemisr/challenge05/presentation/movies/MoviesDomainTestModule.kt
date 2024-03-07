package banquemisr.challenge05.presentation.movies

import banquemisr.challenge05.domain.di.DomainModule
import banquemisr.challenge05.domain.dto.DataState
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.domain.dto.movies.Movie
import banquemisr.challenge05.domain.dto.movies.MoviesDTO
import banquemisr.challenge05.domain.usecase.getplayingmovies.IGetPlayingMoviesUseCase
import banquemisr.challenge05.domain.usecase.getpopularmovies.IGetPopularMoviesUseCase
import banquemisr.challenge05.domain.usecase.getupcomingmovies.IGetUpcomingMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [DomainModule::class]
)
object MoviesDomainTestModule {

    @Provides
    @ViewModelScoped
    fun provideIGetUpcomingMoviesSuccessUseCase() = object  : IGetUpcomingMoviesUseCase {
        override suspend fun getUpcomingMovies(page: Int): Flow<DataState<MoviesDTO>> {
            return flowOf(DataState.Success(MoviesDTO(movies = arrayListOf(Movie(title = "a")))))
        }
    }
    @Provides
    @ViewModelScoped
    fun provideIGetPlayingMoviesUseCase() = object  : IGetPlayingMoviesUseCase {
        override suspend fun getPlayingMovies(page: Int): Flow<DataState<MoviesDTO>> {
            return flowOf(
                DataState.Error<DataState<MoviesDTO>>(
                errorType = ErrorType.GeneralError ,
                errorModel = ErrorModel.GeneralError(1,"error")
                )
            )
        }
    }
    @Provides
    @ViewModelScoped
    fun provideIGetPopularMoviesUseCase() = object  : IGetPopularMoviesUseCase {
        override suspend fun getPopularMovies(page: Int): Flow<DataState<MoviesDTO>> {
            return flowOf(DataState.Success(MoviesDTO(movies = arrayListOf(Movie(title = "a")))))
        }
    }
}