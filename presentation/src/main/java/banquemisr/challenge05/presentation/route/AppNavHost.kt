package banquemisr.challenge05.presentation.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import banquemisr.challenge05.presentation.base.Routes
import banquemisr.challenge05.presentation.screens.details.DetailsScreen
import banquemisr.challenge05.presentation.screens.home.HomeScreen
import banquemisr.challenge05.presentation.screens.home.viewmodel.MoviesViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.Movies.MOVIES_HOME,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Movies.MOVIES_HOME) {
            val viewModel: MoviesViewModel = hiltViewModel()
            HomeScreen(navController , viewModel)
        }
        composable(
            Routes.Movies.MOVIES_DETAILS,
            arguments = listOf(navArgument(Routes.Paths.MOVIE_DETAILS_ID) { type = NavType.StringType }),
        ) {backStackEntry->
            DetailsScreen( navController, backStackEntry.arguments?.getString(Routes.Paths.MOVIE_DETAILS_ID),)
        }

    }
}