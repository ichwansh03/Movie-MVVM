package com.ichwan.moviemvvm.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.ichwan.moviemvvm.BuildConfig

/**
 * provides configure inter API's
 */
class ApiConfig {

    companion object {
        fun getApiService(): ApiService {
            //validate whetever data successufully appear or not in logcat
            val logInceptor = if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

            val client = OkHttpClient.Builder()
                .addInterceptor(logInceptor)
                .build()


             // fetch a JSON response from web service and return it as a string
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}