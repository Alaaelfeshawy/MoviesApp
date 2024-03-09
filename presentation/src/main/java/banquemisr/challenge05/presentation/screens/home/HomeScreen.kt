package banquemisr.challenge05.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.components.ErrorComponent
import banquemisr.challenge05.presentation.components.MoviesTopBar
import banquemisr.challenge05.presentation.screens.home.MoviesScreenSemantics.MOVIES_SCREEN_ERROR
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesContract
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesViewModel
import banquemisr.challenge05.presentation.screens.home.widgets.PlayingNowMoviesSection
import banquemisr.challenge05.presentation.screens.home.widgets.PopularMoviesSection
import banquemisr.challenge05.presentation.screens.home.widgets.UpcomingMoviesSection

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MoviesViewModel) {

    val noInternetConnection = viewModel.uiState.collectAsState().value.errorType == ErrorType.NoInternetConnection
    val playingMoviesState = viewModel.uiState.collectAsStateWithLifecycle().value.playingMoviesState
    val popularMoviesState = viewModel.uiState.collectAsStateWithLifecycle().value.popularMoviesState
    val upcomingMoviesState = viewModel.uiState.collectAsStateWithLifecycle().value.upcomingMoviesState

    LaunchedEffect(key1 = Unit ){
        viewModel.setEvent(MoviesContract.Event.GetHomeData(LoadingType.FullLoading))

    }
    Scaffold(
        topBar = {
            MoviesTopBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (noInternetConnection){
                ErrorComponent(
                    modifier = Modifier.fillMaxHeight().semantics {
                        testTag = MOVIES_SCREEN_ERROR
                    }
                )
            }else{
                UpcomingMoviesSection(upcomingMoviesState , navController){
                    viewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.PaginationLoading))
                }
                PopularMoviesSection(popularMoviesState , navController){
                    viewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.PaginationLoading))
                }
                PlayingNowMoviesSection(playingMoviesState , navController){
                    viewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.PaginationLoading))
                }
            }
        }
    }

}

