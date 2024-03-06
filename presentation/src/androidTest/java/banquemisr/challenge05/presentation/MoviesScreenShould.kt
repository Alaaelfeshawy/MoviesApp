package banquemisr.challenge05.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import banquemisr.challenge05.HiltTestActivity
import banquemisr.challenge05.presentation.route.AppNavHost
import banquemisr.challenge05.presentation.screens.home.MoviesScreenSemantics
import banquemisr.challenge05.presentation.screens.home.MoviesScreenSemantics.MoviesListSemantics.UPCOMING_MOVIES_LIST_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MoviesScreenShould {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
    }
    @Test
    fun fullLoadingStateIsActive() {
        composeTestRule.onNodeWithTag(MoviesScreenSemantics.MoviesListSemantics.MOVIES_FULL_LOADING_FOR_POPULAR_MOVIES_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(MoviesScreenSemantics.MoviesListSemantics.MOVIES_FULL_LOADING_FOR_UPCOMING_MOVIES_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun pagingLoadingStateIsActive() = runTest{
        advanceUntilIdle()
        composeTestRule.onNodeWithTag("Upcoming movies").isDisplayed()
        advanceUntilIdle()
        composeTestRule.onNodeWithTag(UPCOMING_MOVIES_LIST_TAG).
        performScrollToIndex(20).isDisplayed()
    }


    @Test
    fun errorIsDisplayedWhenErrorHappen() {
        composeTestRule.onNodeWithTag(MoviesScreenSemantics.MOVIES_SCREEN_ERROR).assertIsDisplayed()
    }

    @Test
    fun listIsDisplayed() = runTest{
        advanceUntilIdle()
        composeTestRule.onNodeWithTag("Upcoming movies").isDisplayed()
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag(UPCOMING_MOVIES_LIST_TAG).isDisplayed()

    }

    @Test
    fun onMovieItemClick() {

        composeTestRule.onAllNodes(hasTestTag(UPCOMING_MOVIES_LIST_TAG))[0].performClick()

    }
}