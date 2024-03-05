package banquemisr.challenge05.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        topBar = {
            MoviesTopBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
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

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MoviesTopBar(title : String = "Home",
                 isHome : Boolean = true,
                 onBackButtonClicked : ()->Unit={}
                         ) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
            )
        },
        navigationIcon = {
            if (isHome){
                Box {

                }
            }else{
               IconButton(onClick = {
                   onBackButtonClicked.invoke()
               }) {
                   Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                       contentDescription = null)
               }
        }
        }
    )
}
