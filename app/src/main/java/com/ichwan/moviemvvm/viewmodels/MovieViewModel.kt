package com.ichwan.moviemvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ichwan.moviemvvm.api.RequestState
import com.ichwan.moviemvvm.models.MovieResponse
import com.ichwan.moviemvvm.repositories.MovieRepository
import retrofit2.HttpException

class MovieViewModel: ViewModel() {

    private val repository: MovieRepository = MovieRepository()
    private var page = 1

    fun getPopularMovie(): LiveData<RequestState<MovieResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = repository.getPopularMovie(page)
            emit(RequestState.Success(response))
        } catch (e: HttpException){
            e.response()?.errorBody()?.string()?.let { RequestState.Error(it) }?.let { emit(it) }
        }
    }
}