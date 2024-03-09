package banquemisr.challenge05.presentation.utils.extensions

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import banquemisr.challenge05.presentation.base.LoadingType
import banquemisr.challenge05.presentation.utils.Constants

@Composable
fun LoadingType?.ShowLoader(modifier: Modifier = Modifier) {
    if (this != null)
        if (this == LoadingType.FullLoading) Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
}

@Composable
fun String?.getFullPathImage(): String {
    return "${Constants.BASE_IMAGE_URL}$this"
}
