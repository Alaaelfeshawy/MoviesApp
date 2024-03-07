package banquemisr.challenge05.presentation.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import banquemisr.challenge05.domain.dto.moviedetails.MovieDetailsDTO
import banquemisr.challenge05.domain.dto.movies.Movie
import banquemisr.challenge05.presentation.components.ErrorComponent
import banquemisr.challenge05.presentation.components.MoviesTopBar
import banquemisr.challenge05.presentation.components.StyledText
import banquemisr.challenge05.presentation.screens.details.viewmodel.MovieDetailsViewModel
import banquemisr.challenge05.presentation.screens.home.widgets.MovieItem
import banquemisr.challenge05.presentation.utils.extensions.ShowLoader

@Composable
fun DetailsScreen(
    navController: NavHostController,
    viewModel: MovieDetailsViewModel
) {

    val movie = viewModel.uiState.collectAsState().value.movieDetailsDTO
    val loadingType = viewModel.uiState.collectAsState().value.loadingType
    val error = viewModel.uiState.collectAsState().value.errorModel

    Scaffold(
        topBar = {
            MoviesTopBar(title = "Details",isHome = false){
                navController.popBackStack()
            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = Alignment.Center
            ) {
            loadingType.ShowLoader(
                modifier = Modifier.fillMaxSize()
            )

            if (error != null){
                ErrorComponent(
                    errorModel = error
                )
            }
            if (movie != null){
                MovieDetails(movie)
            }

        }
    }
}

@Composable
private fun MovieDetails(movie: MovieDetailsDTO?) {
    Column(modifier = Modifier.fillMaxSize()) {
        MovieItem(
            modifier = Modifier, item = Movie(
                title = movie?.title,
                moviePoster = movie?.backdropPath,
                voteAverage = movie?.voteAverage

            ), onMovieClicked = {})

        TimeAndGenresSection(movie)
        Text(
            text = movie?.overView.toString(),
            Modifier.padding(horizontal = 12.dp),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(6.dp))
        LanguageSection(movie)
    }
}

@Composable
private fun LanguageSection(movie: MovieDetailsDTO?) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Languages: ")
            }
            withStyle(SpanStyle()) {
                append(movie?.language.toString())
            }
        },
        modifier = Modifier.padding(horizontal = 12.dp),
    )
}

@Composable
private fun TimeAndGenresSection(movie: MovieDetailsDTO?) {
    LazyRow {
        item {
            StyledText(text = movie?.runTime.toString() + " min")
        }

        items(movie?.genres ?: emptyList()) { genre ->
            StyledText(text = genre.toString())
        }

    }
}


