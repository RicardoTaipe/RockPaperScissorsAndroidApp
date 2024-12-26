package com.example.rockpaperscissorsapp.play

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.RockPaperScissorsApplication
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.di.AppContainer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class PlayFragmentTest {
    @Mock
    private lateinit var mockContainer: AppContainer

    @Mock
    private lateinit var gameRepository: GameRepository

    @Before
    fun registerIdlingResource() {
        MockitoAnnotations.openMocks(this)
        getApplicationContext<RockPaperScissorsApplication>().container = mockContainer
        `when`(mockContainer.gameRepository).thenReturn(gameRepository)
    }

    @Test
    fun clickRockButton_navigateToGameFragment() {
        testButtonNavigation(R.id.rock_iv)
        verify(gameRepository).userChoice = Choice.ROCK
    }

    @Test
    fun clickPaperButton_navigateToGameFragment() {
        testButtonNavigation(R.id.paper_iv)
        verify(gameRepository).userChoice = Choice.PAPER
    }

    @Test
    fun clickScissorButton_navigateToGameFragment() {
        testButtonNavigation(R.id.scissors_iv)
        verify(gameRepository).userChoice = Choice.SCISSORS
    }

    // Helper function to test button click and navigation
    private fun testButtonNavigation(buttonId: Int) {
        val scenario =
            launchFragmentInContainer<PlayFragment>(Bundle(), R.style.Theme_RockPaperScissorsApp)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(buttonId)).perform(click())
        verify(navController).navigate(PlayFragmentDirections.actionPlayFragmentToGameFragment())
    }
}