package com.ichwan.moviemvvm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ichwan.moviemvvm.databinding.ActivityMovieListBinding

class MovieList : AppCompatActivity() {
    private var _binding: ActivityMovieListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}