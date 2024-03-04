package banquemisr.challenge05.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import banquemisr.challenge05.presentation.base.LoadingType

@Composable
fun HomeScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value

    val lazyListState: LazyListState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 2
        }
    }
    if (isScrollToEnd)
        viewModel.setEvent(MoviesContract.Event.GetPlayingMovies(LoadingType.PaginationLoading))

    Column {
        LazyColumn(
            state = lazyListState
        ){
            state.movies?.size?.let {
                items(it) { Text(
                    text =  state.movies.get(it).title.toString(),
                    modifier = Modifier.padding(20.dp)
                    ) }
            }
        }
    }
}