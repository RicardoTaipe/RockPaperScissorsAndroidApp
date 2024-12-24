package com.example.rockpaperscissorsapp.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.databinding.FragmentPlayBinding
import com.example.rockpaperscissorsapp.game.GameViewModel


class PlayFragment : Fragment() {
    private lateinit var binding: FragmentPlayBinding
    private val gameViewModel: GameViewModel by activityViewModels { GameViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPlayBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            paperIv.setOnClickListener {
                navigateToGameFragment(Choice.PAPER)
            }
            rockIv.setOnClickListener {
                navigateToGameFragment(Choice.ROCK)
            }
            scissorsIv.setOnClickListener {
                navigateToGameFragment(Choice.SCISSORS)
            }
        }
    }

    private fun navigateToGameFragment(option: Choice) {
        gameViewModel.selectOption(option)
        findNavController().navigate(PlayFragmentDirections.actionPlayFragmentToGameFragment())

    }
}