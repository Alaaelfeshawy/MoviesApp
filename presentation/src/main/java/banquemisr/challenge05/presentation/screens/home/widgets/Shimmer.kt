package banquemisr.challenge05.presentation.screens.home.widgets

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import banquemisr.challenge05.presentation.components.NetworkImage

@Preview(showBackground = true)
@Composable
fun MovieItemShimmer(
    modifier: Modifier = Modifier,
    isTitleVisible : Boolean = true
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (isTitleVisible){
            Text(text = "" ,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(12.dp)
                    .width(150.dp)
                    .background(brush = shimmerBrush())
            )
        }else{
            Box{}
        }

        Row {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .width(150.dp)
                    .heightIn(max = 250.dp),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 5.dp,
            ) {

                Box(modifier = modifier.fillMaxSize()) {
                    NetworkImage(imageUrl = "",modifier.background(brush = shimmerBrush()))
                }
            }
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .width(150.dp)
                    .heightIn(max = 250.dp),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 5.dp,
            ) {

                Box(modifier = modifier.fillMaxSize()) {
                    NetworkImage(imageUrl = "",modifier.background(brush = shimmerBrush()))
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun shimmerBrush(showShimmer: Boolean = true,targetValue:Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}
