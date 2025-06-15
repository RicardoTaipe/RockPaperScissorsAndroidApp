package com.example.rockpaperscissorsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.databinding.ActivityMainBinding
import com.example.rockpaperscissorsapp.game.GameViewModel
import com.example.rockpaperscissorsapp.rules.RulesFragment

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) { _  ->
                    Navigation()
                }
            }
        }

    }

}
