package com.example.rockpaperscissorsapp


import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.rockpaperscissorsapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val activity get() = composeTestRule.activity

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

//    @Test
//    fun whenRulesClick_thenRulesOpen() {
//        onView(
//            withId(R.id.rules_button)
//        ).perform(click())
//        //rules fragment should be visible
//        onView(withId(R.id.rules_image)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun whenRulesIsOpen_thenCloseIt() {
//        //open rules fragment
//        onView(
//            withId(R.id.rules_button)
//        ).perform(click())
//        onView(withId(R.id.close_button)).perform(click())
//        //verify rules fragment is closed
//        onView(withId(R.id.rules_image)).check(doesNotExist())
//    }
//
//    @Test
//    fun whenUserSelectAnOption_thenShowCompChoice() {
//        onView(
//            withId(R.id.paper_iv)
//        ).perform(click())
//        onView(withId(R.id.user_choice)).check(matches(isDisplayed()))
//        onView(withId(R.id.com_choice)).check(matches(isDisplayed()))
//        onView(withId(R.id.result)).check(matches(isDisplayed()))
//    }
}
