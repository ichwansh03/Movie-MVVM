package com.ichwan.moviemvvm.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.ichwan.moviemvvm.adapters.MovieListAdapter
import com.ichwan.moviemvvm.api.network.RequestState
import com.ichwan.moviemvvm.databinding.ActivityMovieListBinding
import com.ichwan.moviemvvm.listener.OnMovieClickListener
import com.ichwan.moviemvvm.models.entity.Movies
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

        observeGenres()
        viewModel.getPopularMovie()
        observePopularMovies()
        setupRecyclerView()

        adapter?.onMovieClickListener(object : OnMovieClickListener{
            override fun onMovieClick(movies: Movies, genres: String) {
                val intent = Intent(this@MovieList, MovieDetail::class.java)
                intent.putExtra(MovieDetail.movie, movies)
                intent.putExtra(MovieDetail.genres, genres)
                startActivity(intent)
            }
        })

        binding.searchButton.setOnClickListener {
            val query = binding.search.text.toString()
            when {
                query.isEmpty() -> binding.search.error = "Please insert a keyword"
                else -> {
                    val intent = Intent(this, SearchMovie::class.java)
                    intent.putExtra(SearchMovie.query, query)
                    startActivity(intent)
                }
            }
        }
    }

    private fun observePopularMovies() {
        viewModel.popularResponse.observe(this) {
            if (it != null) {
                when(it) {
                    is RequestState.Loading -> showLoading()
                    is RequestState.Success -> {
                        hideLoading()
                        it.data?.results?.let { data -> adapter?.differ?.submitList(data.toList()) }
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeGenres() {
        viewModel.getGenres().observe(this) {
            if (it != null) {
                when(it) {
                    is RequestState.Loading -> {}
                    is RequestState.Success -> it.data.genres?.let { data -> adapter?.setGenres(data) }
                    is RequestState.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private val scrollListener = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                viewModel.getPopularMovie()
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = MovieListAdapter()
        manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            movieList.adapter = adapter
            movieList.layoutManager = manager
            movieList.addOnScrollListener(scrollListener)
        }
    }

    private fun showLoading() {
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.loading.hide()
    }

}