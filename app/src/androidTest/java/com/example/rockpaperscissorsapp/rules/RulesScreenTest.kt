package com.example.rockpaperscissorsapp.rules

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class RulesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val activity get() = composeTestRule.activity

    @Test
    fun rulesScreen_elementsAreDisplayed() {
        setupRulesScreen(onDismiss = {})

        composeTestRule.onNodeWithText(R.string.rules()).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(R.string.rules_image()).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(R.string.close_button()).assertIsDisplayed()
    }

    @Test
    fun rulesScreen_clickClose_callsOnDismissRequest() {
        var dismissCalled = false
        setupRulesScreen(onDismiss = { dismissCalled = true })

        composeTestRule.onNodeWithContentDescription(R.string.close_button()).performClick()

        assertTrue(dismissCalled)
    }

    private fun setupRulesScreen(onDismiss: () -> Unit) {
        composeTestRule.setContent {
            MyApplicationTheme {
                RulesScreen(onDismissRequest = onDismiss)
            }
        }
    }

    private operator fun Int.invoke(): String = activity.getString(this)
}