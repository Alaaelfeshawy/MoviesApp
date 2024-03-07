package banquemisr.challenge05.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun NetworkImage(imageUrl: String , modifier: Modifier = Modifier) {

    AsyncImage(
        contentScale = ContentScale.FillBounds,
        modifier = modifier.fillMaxSize(),
        model = imageUrl,
        contentDescription = ""
    )
}