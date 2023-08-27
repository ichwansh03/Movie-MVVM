package com.ichwan.moviemvvm.repositories

import com.ichwan.moviemvvm.BuildConfig
import com.ichwan.moviemvvm.api.ApiConfig

/**
 * provides service from ApiConfig class (client config) then
 * return values from ApiService.
 * use suspend function for viewmodels.launch later (coroutines)
 */
class MovieRepository {

    private val client = ApiConfig.getApiService()

    suspend fun getPopularMovie(page: Int) = client.getPopularMovie(BuildConfig.API_KEY, page)

    suspend fun getSearchMovie(query: String, page: Int) = client.getSearchMovie(BuildConfig.API_KEY, query, page)

    suspend fun getGenres() = client.getGenres(BuildConfig.API_KEY)
}