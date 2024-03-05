package banquemisr.challenge05.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(navController: NavHostController, movieId: String?) {

    Column {
        Text(text = "Details $movieId")

    }
}
