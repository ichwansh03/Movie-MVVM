package com.ichwan.moviemvvm.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ichwan.moviemvvm.adapters.MovieListAdapter
import com.ichwan.moviemvvm.api.network.RequestState
import com.ichwan.moviemvvm.databinding.ActivitySearchMovieBinding
import com.ichwan.moviemvvm.listener.OnMovieClickListener
import com.ichwan.moviemvvm.models.entity.Movies
import com.ichwan.moviemvvm.viewmodels.MovieViewModel

class SearchMovie : AppCompatActivity() {

    private lateinit var binding: ActivitySearchMovieBinding
    private var isSearchAgain = false

    private var adapter: MovieListAdapter? = null
    private var manager: RecyclerView.LayoutManager? = null

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.setText(intent.getStringExtra(query))

        if (!isSearchAgain) viewModel.getSearchMovie(binding.search.text.toString())

        binding.searchButton.setOnClickListener {
            val query = binding.search.text.toString()
            when {
                query.isEmpty() -> binding.search.error = "Please insert a keyword"
                else -> {
                    isSearchAgain = true
                    viewModel.getSearchMovie(query)
                }
            }
        }

        observeGenres()
        observeSearchMovies()
        setupRecyclerView()

        adapter?.onMovieClickListener(object : OnMovieClickListener {
            override fun onMovieClick(movies: Movies, genres: String) {
                val intent = Intent(this@SearchMovie, MovieDetail::class.java)
                intent.putExtra(MovieDetail.movie, movies)
                intent.putExtra(MovieDetail.genres, genres)
                startActivity(intent)
            }
        })
    }

    private fun observeSearchMovies() {
        viewModel.searchResponse.observe(this) {
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

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                viewModel.getSearchMovie(binding.search.text.toString())
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


    companion object {
        const val query = "query"
    }
}