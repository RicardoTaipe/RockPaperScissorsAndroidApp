package com.example.rockpaperscissorsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.rockpaperscissorsapp.databinding.ActivityMainBinding
import com.example.rockpaperscissorsapp.model.GameViewModel

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gameViewModel by viewModels<GameViewModel> { GameViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.lifecycleOwner = this
//        binding.viewModel = gameViewModel
//        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        binding.rulesButton.setOnClickListener {
//            RulesFragment().show(supportFragmentManager, RulesFragment.TAG)
//        }
        installSplashScreen()
        setContent {
            RulesScreen()
        }
    }

}
