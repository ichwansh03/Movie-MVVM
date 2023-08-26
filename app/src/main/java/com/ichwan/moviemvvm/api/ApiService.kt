package com.ichwan.moviemvvm.api

import com.ichwan.moviemvvm.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * nge-provide endpoint dari API
 */

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") key: String?,
        @Query("page") page: Int?
    ): MovieResponse
}