package com.example.rockpaperscissorsapp.play

import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlayViewModelTest {

    @Mock
    lateinit var mockGameRepository: GameRepository


    private lateinit var playViewModel: PlayViewModel

    @Before
    fun setUp() {
        playViewModel = PlayViewModel(mockGameRepository)
    }

    @Test
    fun `GIVEN user select a choice WHEN selectOption is called THEN yourChoice is saved`() {
        val testChoice = Choice.ROCK

        playViewModel.selectOption(testChoice)

        verify(mockGameRepository).userChoice = testChoice
    }

}