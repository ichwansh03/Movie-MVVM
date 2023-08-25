package com.ichwan.moviemvvm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ichwan.moviemvvm.adapters.MovieListAdapter
import com.ichwan.moviemvvm.api.RequestState
import com.ichwan.moviemvvm.databinding.ActivityMovieListBinding
import com.ichwan.moviemvvm.viewmodels.MovieViewModel

class MovieList : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding

    private var adapter: MovieListAdapter? = null
    private var manager: LayoutManager? = null

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observePopularMovies()
        setupRecyclerView()
    }

    private fun observePopularMovies() {
        viewModel.getPopularMovie().observe(this) {
            if (it != null) {
                when(it) {
                    is RequestState.Loading -> showLoading()
                    is RequestState.Success -> {
                        hideLoading()
                        it.data.results?.let { data -> adapter?.setMovies(data) }
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = MovieListAdapter()
        manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            movieList.adapter = adapter
            movieList.layoutManager = manager
        }
    }

    private fun showLoading() {
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.loading.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}