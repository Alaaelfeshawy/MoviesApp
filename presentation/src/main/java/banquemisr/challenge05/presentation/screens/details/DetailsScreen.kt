package banquemisr.challenge05.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import banquemisr.challenge05.presentation.components.MoviesTopBar

@Composable
fun DetailsScreen(navController: NavHostController, movieId: String?) {

    Scaffold(
        topBar = {
            MoviesTopBar(title = "Details",isHome = false){
                navController.popBackStack()
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Details $movieId")

        }
    }
}
