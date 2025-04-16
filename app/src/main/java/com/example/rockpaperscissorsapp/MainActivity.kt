package com.example.rockpaperscissorsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.rockpaperscissorsapp.databinding.ActivityMainBinding
import com.example.rockpaperscissorsapp.game.GameViewModel
import com.example.rockpaperscissorsapp.rules.RulesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gameViewModel by viewModels<GameViewModel> { GameViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = gameViewModel
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.rulesButton.setOnClickListener {
            RulesFragment().show(supportFragmentManager, RulesFragment.TAG)
        }
        installSplashScreen()
        fixInsetsForEdgeToEdge()
        setStatusBarAppearance()
    }

    private fun fixInsetsForEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appContainer) { v, insets ->
            val innerPadding =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(
                innerPadding.left,
                innerPadding.top,
                innerPadding.right,
                innerPadding.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setStatusBarAppearance() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
                false
        }
    }
}
