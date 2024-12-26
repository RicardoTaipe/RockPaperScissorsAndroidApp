package com.example.rockpaperscissorsapp.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.Result
import com.example.rockpaperscissorsapp.databinding.FragmentGameBinding
import com.google.android.material.imageview.ShapeableImageView

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val gameViewModel: GameViewModel by activityViewModels { GameViewModel.Factory }
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
        return FragmentGameBinding.inflate(inflater, container, false).run {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            viewModel = gameViewModel
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel.playGame()

        gameViewModel.yourChoice.observe(viewLifecycleOwner) { choice ->
            choice?.let { setUiData(binding.userChoice, it) }
        }

        gameViewModel.comChoice.observe(viewLifecycleOwner) { choice ->
            choice?.let {
                setUiData(binding.comChoice, it)
                binding.apply {
                    comChoice.isVisible = true
                    timer.isInvisible = true
                }
            }
        }

        binding.playAgain.setOnClickListener {
            gameViewModel.resetGame()
            findNavController().navigate(GameFragmentDirections.actionGameFragmentToPlayFragment())
        }

        gameViewModel.result.observe(viewLifecycleOwner) { gameResult ->
            gameResult?.let {
                with(binding) {
                    result.text = getString(
                        when (it) {
                            Result.WIN -> R.string.you_win
                            Result.DRAW -> R.string.draw
                            Result.LOSE -> R.string.you_lose
                        }
                    )
                    result.isVisible = true
                    binding.playAgain.isVisible = true
                }
            }
        }
    }

    private fun setUiData(view: ShapeableImageView, data: Choice) {
        val (background, icon) = getUiDataForChoice(data)
        view.apply {
            setImageResource(icon)
            this.background = AppCompatResources.getDrawable(requireContext(), background)
        }
    }

    private fun getUiDataForChoice(data: Choice): Pair<Int, Int> {
        return when (data) {
            Choice.ROCK -> Pair(R.drawable.bg_rock, R.drawable.icon_rock)
            Choice.PAPER -> Pair(R.drawable.bg_paper, R.drawable.icon_paper)
            Choice.SCISSORS -> Pair(R.drawable.bg_scissors, R.drawable.icon_scissors)
        }
    }
}

