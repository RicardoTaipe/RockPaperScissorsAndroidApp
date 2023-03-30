package com.example.rockpaperscissorsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.rockpaperscissorsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setContentView(binding.root)
    }
}
