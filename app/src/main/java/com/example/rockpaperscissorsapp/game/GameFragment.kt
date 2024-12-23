package com.example.rockpaperscissorsapp.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.databinding.FragmentGameBinding
import com.example.rockpaperscissorsapp.data.Choice
import com.google.android.material.imageview.ShapeableImageView
import com.example.rockpaperscissorsapp.data.Result

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    gameViewModel.resetGame()
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })
    }

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
            binding.run {
                comChoice.isVisible = true
                timer.isInvisible = true
            }

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
                        Result.WIN -> R.string.you_win
                        Result.DRAW -> R.string.draw
                        Result.LOSE -> R.string.you_lose
                    }
                )
                isVisible = true
            }
            binding.playAgain.isVisible = true
        }
    }

    private fun setUiData(view: ShapeableImageView, data: Choice) {
        @DrawableRes
        val drawable: Int
        val image: Int

        when (data) {
            Choice.ROCK -> {
                drawable = R.drawable.bg_rock
                image = R.drawable.icon_rock
            }

            Choice.PAPER -> {
                drawable = R.drawable.bg_paper
                image = R.drawable.icon_paper
            }

            Choice.SCISSORS -> {
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

