package banquemisr.challenge05.presentation.moviesDetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import banquemisr.challenge05.HiltTestActivity
import banquemisr.challenge05.presentation.route.AppNavHost
import banquemisr.challenge05.presentation.screens.home.MoviesScreenSemantics
import banquemisr.challenge05.presentation.utils.Constants.APP_BAR_TITLE_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MoviesDetailsScreenShould {

//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    @get: Rule(order = 1)
//    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
//
//    @Before
//    fun setup() {
//        hiltRule.inject()
//        composeTestRule.setContent {
//            val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//            AppNavHost(navController = navController)
//        }
//    }
//
//
//    @Test
//    fun displayAppbarTitle() {
//        composeTestRule.onAllNodes(hasTestTag(MoviesScreenSemantics.MoviesListSemantics.UPCOMING_MOVIES_LIST_TAG))[0].performClick()
//        composeTestRule.onNodeWithTag(APP_BAR_TITLE_TAG).assertIsDisplayed()
//    }
//
//    @Test
//    fun movieDisplayed() = runTest{
//        composeTestRule.onAllNodes(hasTestTag(MoviesScreenSemantics.MoviesListSemantics.UPCOMING_MOVIES_LIST_TAG))[0].performClick()
//        composeTestRule.onNodeWithText("a").isDisplayed()
//    }

//    @Test
//    fun errorIsDisplayedWhenErrorHappen() {
//        composeTestRule.onNodeWithText("error").isDisplayed()
//    }

}