package com.example.rockpaperscissorsapp.game

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rockpaperscissorsapp.R
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class GameFragmentTest {
    @Before
    fun seUp() {
        launchFragmentInContainer<GameFragment>(Bundle(), R.style.Theme_RockPaperScissorsApp)
    }

    @Test
    fun InitalState_NoUserSelection() {
        onView(withId(R.id.user_choice)).check(matches(isDisplayed()))
        onView(withId(R.id.timer)).check(matches(isDisplayed()))
        onView(withId(R.id.com_choice)).check(matches(not(isDisplayed())))
        onView(withId(R.id.play_again)).check(matches(not(isDisplayed())))
        onView(withId(R.id.result)).check(matches(not(isDisplayed())))
    }

    @Test
    fun clickBackButton_navigateToPlayFragment() {
        val scenario =
            launchFragmentInContainer<GameFragment>(Bundle(), R.style.Theme_RockPaperScissorsApp)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        pressBack()

        verify(navController).navigate(GameFragmentDirections.actionGameFragmentToPlayFragment())
    }

    @Test
    fun clickPlayAgainButton_navigateToPlayFragment() {
        val scenario =
            launchFragmentInContainer<GameFragment>(Bundle(), R.style.Theme_RockPaperScissorsApp)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        pressBack()

        verify(navController).navigate(GameFragmentDirections.actionGameFragmentToPlayFragment())
    }
}
