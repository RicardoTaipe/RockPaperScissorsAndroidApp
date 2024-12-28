package com.example.rockpaperscissorsapp.game

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameFragmentTest {

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

    @Before
    fun seUp() {
        launchFragmentInContainer<GameFragment>(Bundle(), R.style.Theme_RockPaperScissorsApp)
    }


    @Test
    fun whenUserSelectAnOption_thenShowCompChoice() {
        onView(withId(R.id.com_choice)).check(matches(isDisplayed()))
        onView(withId(R.id.result)).check(matches(isDisplayed()))
        onView(withId(R.id.play_again)).check(matches(isDisplayed()))
    }
}
