package banquemisr.challenge05.presentation.utils.extensions

import androidx.compose.runtime.Composable
import banquemisr.challenge05.presentation.utils.Constants

@Composable
fun String?.getFullPathImage(): String {
    return "${Constants.BASE_IMAGE_URL}$this"
}
