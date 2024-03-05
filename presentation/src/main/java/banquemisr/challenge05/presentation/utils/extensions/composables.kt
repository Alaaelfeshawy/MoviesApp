package banquemisr.challenge05.presentation.utils.extensions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import banquemisr.challenge05.presentation.R
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
fun LoadingType?.ShowPaginationLoading(modifier: Modifier = Modifier, message: Int?=null) {
    val context = LocalContext.current
    if (this != null)
        if (this == LoadingType.PaginationLoading)
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
                if (message != null)
                    Text(text = context.getString(message))
            }
}


@Composable
fun String?.getFullPathImage(): String {
    return "${Constants.BASE_IMAGE_URL}$this"
}
