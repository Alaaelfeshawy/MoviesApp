package banquemisr.challenge05.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import banquemisr.challenge05.presentation.utils.Constants.APP_BAR_TITLE_TAG

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MoviesTopBar(title : String = "Home",
                 isHome : Boolean = true,
                 onBackButtonClicked : ()->Unit={}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.semantics {
                    testTag = APP_BAR_TITLE_TAG
                }
            )
        },
        navigationIcon = {
            if (isHome){
                Box {}
            }else{
                IconButton(onClick = {
                    onBackButtonClicked.invoke()
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null)
                }
            }
        }
    )
}
