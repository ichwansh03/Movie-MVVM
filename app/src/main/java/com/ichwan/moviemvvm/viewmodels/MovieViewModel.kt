package com.ichwan.moviemvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ichwan.moviemvvm.api.network.RequestState
import com.ichwan.moviemvvm.models.response.GenreResponse
import com.ichwan.moviemvvm.models.response.MovieResponse
import com.ichwan.moviemvvm.repositories.MovieRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

class MovieViewModel: ViewModel() {

    private val repository: MovieRepository = MovieRepository()

    private var popularPage = 1
    private var popularMovieResponse: MovieResponse? = null
    private var _popularResponse = MutableLiveData<RequestState<MovieResponse?>>()
    var popularResponse: LiveData<RequestState<MovieResponse?>> = _popularResponse

    private var searchPage = 1
    private var searchMovieResponse: MovieResponse? = null
    private var _searchResponse = MutableLiveData<RequestState<MovieResponse?>>()
    var searchResponse: LiveData<RequestState<MovieResponse?>> = _searchResponse

    fun getPopularMovie() {
        viewModelScope.launch {
            _popularResponse.postValue(RequestState.Loading)
            val response = repository.getPopularMovie(popularPage)
            _popularResponse.postValue(handlerPopularMovieResponse(response))
        }
    }

    private fun handlerPopularMovieResponse(response: Response<MovieResponse>): RequestState<MovieResponse?> {
        return if (response.isSuccessful) {
            response.body()?.let {
                popularPage++
                if (popularMovieResponse == null) popularMovieResponse = it
                else {
                    val oldMovies = popularMovieResponse?.results
                    val newMovies = it.results
                    oldMovies?.addAll(newMovies)
                }
            }

            RequestState.Success(popularMovieResponse ?: response.body())

        } else RequestState.Error(
            try {
                response.errorBody()?.string()?.let {
                    JSONObject(it).get("status_message")
                }
            } catch (e: JSONException) {
                e.localizedMessage
            } as String
        )
    }

    fun getSearchMovie(query: String) {
        viewModelScope.launch {
            _searchResponse.postValue(RequestState.Loading)
            val response = repository.getSearchMovie(query, searchPage)
            _searchResponse.postValue(handlerSearchMovieResponse(response))
        }
    }

    private fun handlerSearchMovieResponse(response: Response<MovieResponse>): RequestState<MovieResponse?> {
        return if (response.isSuccessful) {
            response.body()?.let {
                searchPage++
                if (searchMovieResponse == null) searchMovieResponse = it
                else {
                    val oldMovies = searchMovieResponse?.results
                    val newMovies = it.results
                    oldMovies?.addAll(newMovies)
                }
            }

            RequestState.Success(searchMovieResponse ?: response.body())

        } else RequestState.Error(
            try {
                response.errorBody()?.string()?.let {
                    JSONObject(it).get("status_message")
                }
            } catch (e: JSONException) {
                e.localizedMessage
            } as String
        )
    }

    fun getGenres(): LiveData<RequestState<GenreResponse>> = liveData {
        try {
            val response = repository.getGenres()
            emit(RequestState.Success(response))
        } catch (e: HttpException){
            e.response()?.errorBody()?.string()?.let { RequestState.Error(it) }?.let { emit(it) }
        }
    }
}