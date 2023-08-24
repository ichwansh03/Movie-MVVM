package com.ichwan.moviemvvm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ichwan.moviemvvm.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}