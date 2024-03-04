package banquemisr.challenge05.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun NetworkImage(imageUrl: String , modifier: Modifier = Modifier) {

    val painter = rememberAsyncImagePainter(imageUrl)

    Image(
        painter = painter,
        contentDescription = null, // Provide content description if needed
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))

    )
}