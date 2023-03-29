package com.example.rockpaperscissorsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.rockpaperscissorsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.paperIv.setOnClickListener { }
        binding.rockIv.setOnClickListener { }
        binding.scissorsIv.setOnClickListener { }

        setContentView(binding.root)
    }
}

//binding.paperIv.background =
//AppCompatResources.getDrawable(applicationContext, R.drawable.bg_scissors)