package com.ichwan.moviemvvm.repositories

import com.ichwan.moviemvvm.BuildConfig
import com.ichwan.moviemvvm.api.ApiConfig


class MovieRepository {

    private val client = ApiConfig.getApiService()

    suspend fun getPopularMovie(page: Int) = client.getPopularMovie(BuildConfig.API_KEY, page)
}