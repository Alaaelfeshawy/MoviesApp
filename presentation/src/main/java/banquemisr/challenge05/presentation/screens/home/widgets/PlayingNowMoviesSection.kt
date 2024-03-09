package banquemisr.challenge05.presentation.screens.home.widgets

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.base.Routes
import banquemisr.challenge05.presentation.base.Routes.setPatArgumentsToRoutes
import banquemisr.challenge05.presentation.screens.home.MoviesScreenSemantics
import banquemisr.challenge05.presentation.screens.home.viewmodel.HomeState
import banquemisr.challenge05.presentation.utils.extensions.getStringFromMessage

@Composable
fun PlayingNowMoviesSection(state: HomeState, navController: NavHostController ,
                            onPaginationRequest : ()->Unit) {
    val movies = state.movies
    val loadingType = state.loadingType
    val errorModel = state.errorModel?.errorMessage
    val context = LocalContext.current
    val lazyListState: LazyListState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            val totalItemsCount = lazyListState.layoutInfo.totalItemsCount
            val firstVisibleItemIndex = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
            val visibleItemsCount = lazyListState.layoutInfo.visibleItemsInfo.size
            firstVisibleItemIndex + visibleItemsCount >= totalItemsCount && !movies.isNullOrEmpty() && loadingType ==LoadingType.None        }
    }
    if (isScrollToEnd) {
        onPaginationRequest.invoke()
    }
    LaunchedEffect(key1 = errorModel){
        if (errorModel != null){
            Toast.makeText(context,errorModel.getStringFromMessage(context),Toast.LENGTH_SHORT).show()
        }
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
                    LoadingType.FullLoading -> MovieItemShimmer(
                        modifier = Modifier.semantics {
                            testTag = MoviesScreenSemantics.MoviesListSemantics.MOVIES_FULL_LOADING_FOR_PLAYING_MOVIES_TAG
                        }
                    )

                    LoadingType.PaginationLoading -> MovieItemShimmer(isTitleVisible = false)

                    else -> {}
                }
            }

        }
    }
}