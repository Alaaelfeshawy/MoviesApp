package banquemisr.challenge05.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesContract
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MoviesViewModel) {

    val noInternetConnection = viewModel.uiState.collectAsState().value.errorType == ErrorType.NoInternetConnection

    Column {
        if (noInternetConnection){
            Text(text = "no internet")
        }else{
            Text(text = "Upcoming")
            UpcomingMoviesSection(viewModel)
            Text(text = "Now playing")
            PlayingNowMoviesSection(viewModel)
            Text(text = "Popular")
            PopularMoviesSection(viewModel)
        }

    }
}

@Composable
fun UpcomingMoviesSection(viewModel: MoviesViewModel) {
    val state = viewModel.uiState.collectAsState().value
    val movies = state.upcomingMoviesState.movies
    val lazyListState: LazyListState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
    }
    if (isScrollToEnd) {
        viewModel.setEvent(MoviesContract.Event.GetUpcomingMovies(LoadingType.PaginationLoading))
    }

    LazyRow(
        state = lazyListState
    ){
        items(movies?.size ?:0){
            Text(
                text =  movies?.get(it)?.title.toString(),
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}
@Composable
fun PlayingNowMoviesSection(viewModel: MoviesViewModel) {
    val state = viewModel.uiState.collectAsState().value
    val movies = state.playingMoviesState.movies
    val listState: LazyListState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount-1)
        }
    }
    if (isScrollToEnd) {
        viewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.PaginationLoading))
    }

    LazyRow(
        state = listState
    ){
        items(movies?.size ?:0){
            Text(
                text =  movies?.get(it)?.title.toString(),
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}
@Composable
fun PopularMoviesSection(viewModel: MoviesViewModel) {
    val state = viewModel.uiState.collectAsState().value
    val movies = state.popularMoviesState.movies
    val listState: LazyListState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount-1)
        }
    }
    if (isScrollToEnd) {
        viewModel.setEvent(MoviesContract.Event.GetPopularMovies(LoadingType.PaginationLoading))
    }

    LazyRow(
        state = listState
    ){
        items(movies?.size ?:0){
            Text(
                text =  movies?.get(it)?.title.toString(),
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}