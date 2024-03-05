package banquemisr.challenge05.presentation.screens.home.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.Routes
import banquemisr.challenge05.presentation.base.Routes.setPatArgumentsToRoutes
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesContract
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesViewModel
import banquemisr.challenge05.presentation.utils.extensions.ShowPaginationLoading

@Composable
fun PlayingNowMoviesSection(viewModel: MoviesViewModel, navController: NavHostController) {
    val state = viewModel.uiState.collectAsState().value
    val movies = state.playingMoviesState.movies
    val loadingType = state.playingMoviesState.loadingType
    val errorModel = state.playingMoviesState.errorModel?.errorMessage.toString()

    val lazyListState: LazyListState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
    }
    if (isScrollToEnd) {
        viewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.PaginationLoading))
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (!movies.isNullOrEmpty()){
            Text(text = "Playing now movies" ,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(12.dp)
            )
        }else{
            Box{}
        }
        LazyRow(
            state = lazyListState
        ){
            items(movies?.size ?:0){
                val item = movies?.get(it)
                MovieItem(item = item){
                        id ->
                    val route = Routes.Movies.MOVIES_DETAILS.setPatArgumentsToRoutes(
                        Routes.Paths.MOVIE_DETAILS_ID, id.toString()
                    )
                    navController.navigate(route)
                }

            }
            item(key = loadingType) {
                when (loadingType) {
                    LoadingType.FullLoading -> MovieItemShimmer()

                    LoadingType.PaginationLoading -> loadingType.ShowPaginationLoading(
                        modifier = Modifier.fillMaxSize()
                    )

                    else -> {}
                }
            }

        }
    }
}