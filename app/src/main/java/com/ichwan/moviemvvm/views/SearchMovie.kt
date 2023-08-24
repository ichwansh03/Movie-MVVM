package com.ichwan.moviemvvm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ichwan.moviemvvm.databinding.ActivitySearchMovieBinding

class SearchMovie : AppCompatActivity() {
    private var _binding: ActivitySearchMovieBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}