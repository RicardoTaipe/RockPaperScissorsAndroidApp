package com.example.rockpaperscissorsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.rockpaperscissorsapp.databinding.FragmentPlayBinding
import com.example.rockpaperscissorsapp.model.GameViewModel


class PlayFragment : Fragment() {
    private lateinit var binding: FragmentPlayBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    private val game = Game()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.paperIv.setOnClickListener {
            Toast.makeText(context, game.play(Game.Choice.PAPER), Toast.LENGTH_SHORT)
                .show()
        }
        binding.rockIv.setOnClickListener {
            Toast.makeText(context, game.play(Game.Choice.ROCK), Toast.LENGTH_SHORT)
                .show()
        }
        binding.scissorsIv.setOnClickListener {
            Toast.makeText(context, game.play(Game.Choice.SCISSORS), Toast.LENGTH_SHORT)
                .show()
        }
    }
}