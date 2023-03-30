package com.example.rockpaperscissorsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rockpaperscissorsapp.databinding.FragmentGameBinding
import com.example.rockpaperscissorsapp.model.GameViewModel
import com.google.android.material.imageview.ShapeableImageView


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = gameViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel.yourChoice.observe(viewLifecycleOwner) {
            it ?: return@observe
            setUiData(binding.userChoice, it)
        }

        gameViewModel.comChoice.observe(viewLifecycleOwner) {
            it ?: return@observe
            setUiData(binding.comChoice, it)
            binding.comChoice.visibility = View.VISIBLE
            binding.timer.visibility = View.INVISIBLE

        }

        binding.playAgain.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_playFragment)
            gameViewModel.resetGame()
        }

        gameViewModel.result.observe(viewLifecycleOwner) {
            it ?: return@observe
            binding.result.apply {
                text = getString(
                    when (it) {
                        Game.Result.WIN -> R.string.you_win
                        Game.Result.DRAW -> R.string.draw
                        Game.Result.LOSE -> R.string.you_lose
                    }
                )
                visibility = View.VISIBLE
            }
            binding.playAgain.visibility = View.VISIBLE
        }
    }

    private fun setUiData(view: ShapeableImageView, data: Game.Choice) {
        @DrawableRes
        val drawable: Int
        val image: Int

        when (data) {
            Game.Choice.ROCK -> {
                drawable = R.drawable.bg_rock
                image = R.drawable.icon_rock
            }
            Game.Choice.PAPER -> {
                drawable = R.drawable.bg_paper
                image = R.drawable.icon_paper
            }
            Game.Choice.SCISSORS -> {
                drawable = R.drawable.bg_scissors
                image = R.drawable.icon_scissors
            }
        }

        view.apply {
            setImageResource(image)
            background = AppCompatResources.getDrawable(requireContext(), drawable)
        }
    }
}

