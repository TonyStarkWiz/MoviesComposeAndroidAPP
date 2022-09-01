package com.example.moviescomposeapp.network

import com.example.moviescomposeapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MovieRepo {
    fun getMovies(): Flow<ResultState>
}

class MovieRepoImpl(
    private val moviesService: MovieApi =
        Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
) : MovieRepo {
    override fun getMovies(): Flow<ResultState> = flow {
        emit(ResultState.LOADING)

        try {
            val response = moviesService.getMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultState.SUCCESS(it))
                } ?: throw Exception("RESPONSE IS NULL")
            } else {
                throw Exception("FAILURE RESPONSE")
            }
        } catch (e: Exception) {
            emit(ResultState.ERROR(e))
        }
    }

}