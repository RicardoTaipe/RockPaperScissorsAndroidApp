package com.example.rockpaperscissorsapp


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        dataBindingIdlingResource.monitorActivity(mActivityScenarioRule.scenario)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun whenRulesClick_thenRulesOpen() {
        onView(
            withId(R.id.rules_button)
        ).perform(click())

        onView(withId(R.id.rules_image)).check(matches(isDisplayed()))
    }

    @Test
    fun whenRulesIsOpen_thenCloseIt() {
        onView(
            withId(R.id.rules_button)
        ).perform(click())
        onView(withId(R.id.close_button)).perform(click())
        onView(withId(R.id.rules_image)).check(doesNotExist())
    }

    @Test
    fun whenUserSelectAnOption_thenShowCompChoice() {
        onView(
            withId(R.id.paper_iv)
        ).perform(click())
        onView(withId(R.id.com_choice)).check(matches(isDisplayed()))
        onView(withId(R.id.com_choice)).check(matches(isDisplayed()))
        onView(withId(R.id.result)).check(matches(isDisplayed()))
    }
}
