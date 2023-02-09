package ca.josuelubaki.handleeventapp

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import ca.josuelubaki.handleeventapp.ui.theme.HandleEventAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            HandleEventAppTheme {
                MainScreen()
            }
        }
    }

    @Test
    fun shouldDisplayAllText() {

        composeTestRule.onNodeWithText("Total is ...").assertIsDisplayed()
        composeTestRule.onNodeWithText("New Count").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reset").assertIsDisplayed()

        val countButton = SemanticsMatcher.expectValue(
            SemanticsProperties.ContentDescription, listOf("Count Button"),
        )
        composeTestRule.onNode(countButton).assertDoesNotExist()
    }

    @Test
    fun shouldBeSeeCountButtonAfterWrittenTextOnInput(){
        val countButton = SemanticsMatcher.expectValue(
            SemanticsProperties.ContentDescription, listOf("Count Button"),
        )
        composeTestRule.onNode(countButton).assertDoesNotExist()

        composeTestRule.onNodeWithText("New Count").performClick()
        composeTestRule.onNodeWithText("New Count").performTextInput("5")

        composeTestRule.onNode(countButton).assertIsDisplayed()
    }

    @Test
    fun shouldBeDisplayTotalAfterWrittenText() {
        composeTestRule.onNodeWithText("New Count").performClick()
        composeTestRule.onNodeWithText("New Count").performTextInput("5")

        val countButton = SemanticsMatcher.expectValue(
            SemanticsProperties.ContentDescription, listOf("Count Button"),
        )

        composeTestRule.onNode(countButton).performClick()
        composeTestRule.onNodeWithText("Total is 5.0").assertIsDisplayed()
    }
}