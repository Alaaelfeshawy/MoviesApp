package banquemisr.challenge05.presentation.screens.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import banquemisr.challenge05.domain.dto.movies.Movie
import banquemisr.challenge05.presentation.components.NetworkImage
import banquemisr.challenge05.presentation.utils.extensions.formatDecimals
import banquemisr.challenge05.presentation.utils.extensions.getFullPathImage

@Preview(showBackground = true)
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    item: Movie? = Movie(),
    onMovieClicked : (Int)->Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .widthIn(max = 200.dp)
            .heightIn(max = 250.dp)
            .clickable {
                item?.id?.let { onMovieClicked.invoke(it) }
            },
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 5.dp,
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            NetworkImage(imageUrl = item?.moviePoster.getFullPathImage())
            Box(modifier = modifier.align(Alignment.TopEnd)) {
                Icon(
                    modifier = modifier
                        .size(45.dp)
                        .padding(4.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = null ,
                    tint = Color.Yellow,
                )
                Text(
                    text = item?.voteAverage?.formatDecimals() ?: "",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(end = 4.dp)
                )
            }
            Box(modifier = modifier
                .background(color = Color.Black.copy(alpha = 0.7f))
                .align(Alignment.BottomCenter)
                .fillMaxWidth()) {
                Text(
                    modifier = modifier.padding(8.dp),
                    text = item?.title.toString(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
