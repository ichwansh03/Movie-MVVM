package com.ichwan.moviemvvm.listener

import com.ichwan.moviemvvm.models.entity.Movies

interface OnMovieClickListener {
    fun onMovieClick(movies: Movies, genres: String)
}