package banquemisr.challenge05.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import banquemisr.challenge05.domain.dto.ErrorModel
import banquemisr.challenge05.presentation.utils.extensions.getStringFromMessage

@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier,
    errorModel: ErrorModel? = ErrorModel.NoInternetConnection
) {
    val context = LocalContext.current
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (errorModel?.errorIcon != null){
                Image(
                    painter = painterResource(id = errorModel.errorIcon!!),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(
                modifier = Modifier.padding(16.dp, 8.dp),
                text = errorModel?.errorMessage.getStringFromMessage(context),
                style = TextStyle(
                    textAlign = TextAlign.Center ,
                    fontSize = 18.sp
                ),
            )
        }
    }
}