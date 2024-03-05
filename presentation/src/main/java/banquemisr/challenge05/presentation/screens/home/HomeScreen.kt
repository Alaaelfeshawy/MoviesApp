package banquemisr.challenge05.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import banquemisr.challenge05.domain.dto.ErrorType
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesViewModel
import banquemisr.challenge05.presentation.screens.home.widgets.PlayingNowMoviesSection
import banquemisr.challenge05.presentation.screens.home.widgets.PopularMoviesSection
import banquemisr.challenge05.presentation.screens.home.widgets.UpcomingMoviesSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: MoviesViewModel) {

    val noInternetConnection = viewModel.uiState.collectAsState().value.errorType == ErrorType.NoInternetConnection

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            if (noInternetConnection){
                Text(text = "no internet")
            }else{

                UpcomingMoviesSection(viewModel , navController)
                PopularMoviesSection(viewModel , navController)
                PlayingNowMoviesSection(viewModel , navController)
        }
    }
}
