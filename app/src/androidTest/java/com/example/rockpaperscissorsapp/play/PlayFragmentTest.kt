package com.example.rockpaperscissorsapp.play

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rockpaperscissorsapp.R
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class PlayFragmentTest {
    @Test
    fun clickRockButton_navigateToGameFragment() {
        testButtonNavigation(R.id.rock_iv)
    }

    @Test
    fun clickPaperButton_navigateToGameFragment() {
        testButtonNavigation(R.id.paper_iv)
    }

    @Test
    fun clickScissorButton_navigateToGameFragment() {
        testButtonNavigation(R.id.scissors_iv)
    }

    // Helper function to test button click and navigation
    private fun testButtonNavigation(buttonId: Int) {
        val scenario = launchFragmentInContainer<PlayFragment>(Bundle(), R.style.Theme_RockPaperScissorsApp)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(buttonId)).perform(click())
        verify(navController).navigate(PlayFragmentDirections.actionPlayFragmentToGameFragment())
    }
}