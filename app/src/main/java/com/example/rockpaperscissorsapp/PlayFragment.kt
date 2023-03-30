package com.example.rockpaperscissorsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rockpaperscissorsapp.databinding.FragmentPlayBinding
import com.example.rockpaperscissorsapp.model.GameViewModel


class PlayFragment : Fragment() {
    private lateinit var binding: FragmentPlayBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            paperIv.setOnClickListener {
                gameViewModel.selectOption(Game.Choice.PAPER)
                findNavController().navigate(R.id.action_playFragment_to_gameFragment)
            }
            rockIv.setOnClickListener {
                gameViewModel.selectOption(Game.Choice.ROCK)
                findNavController().navigate(R.id.action_playFragment_to_gameFragment)
            }
            scissorsIv.setOnClickListener {
                gameViewModel.selectOption(Game.Choice.SCISSORS)
                findNavController().navigate(R.id.action_playFragment_to_gameFragment)
            }
        }
    }
}