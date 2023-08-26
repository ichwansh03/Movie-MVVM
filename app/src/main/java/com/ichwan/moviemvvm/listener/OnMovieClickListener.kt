package com.ichwan.moviemvvm.listener

import com.ichwan.moviemvvm.models.Genres
import com.ichwan.moviemvvm.models.Movies

interface OnMovieClickListener {
    fun onMovieClick(movies: Movies, genres: String)
}