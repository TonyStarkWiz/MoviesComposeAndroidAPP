package com.example.moviescomposeapp.network

import com.example.moviescomposeapp.model.MoviesItem
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET(MOVIES_LIST_PATH)
    suspend fun getMovies(): Response<List<MoviesItem>>

    companion object {
        const val BASE_URL = "https://howtodoandroid.com/apis/"
        private const val MOVIES_LIST_PATH = "movielist.json"
    }
}