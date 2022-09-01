package com.example.moviescomposeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviescomposeapp.network.MovieRepo
import com.example.moviescomposeapp.network.MovieRepoImpl
import com.example.moviescomposeapp.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepo: MovieRepo = MovieRepoImpl(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _movies: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val movies: LiveData<ResultState> get() = _movies

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch(ioDispatcher) {
            movieRepo.getMovies().collect {
                _movies.postValue(it)
            }
        }
    }
}