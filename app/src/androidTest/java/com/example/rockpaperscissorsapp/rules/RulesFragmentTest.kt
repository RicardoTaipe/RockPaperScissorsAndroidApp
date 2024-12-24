package com.example.rockpaperscissorsapp.rules

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rockpaperscissorsapp.R
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class RulesFragmentTest {

    @Test
    fun clickCloseButton_dismissRulesFragment() {
        val scenario = launchFragmentInContainer<RulesFragment>(Bundle(), R.style.Theme_RockPaperScissorsApp)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        onView(withId(R.id.close_button)).perform(click())

        onView(withId(R.id.rules_image)).check(doesNotExist())
    }

}