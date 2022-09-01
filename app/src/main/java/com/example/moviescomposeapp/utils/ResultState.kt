package com.example.moviescomposeapp.utils

sealed class ResultState {
    object LOADING : ResultState()
    data class SUCCESS<T>(val result: T) : ResultState()
    data class ERROR(val error: Exception): ResultState()
}
