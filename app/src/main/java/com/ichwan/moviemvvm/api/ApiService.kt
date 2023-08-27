package com.ichwan.moviemvvm.api

import com.ichwan.moviemvvm.models.response.GenreResponse
import com.ichwan.moviemvvm.models.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * providing configuration from API Endpoints
 */

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") key: String?,
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("api_key") key: String?,
        @Query("query") query: String?,
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") key: String?
    ): GenreResponse
}