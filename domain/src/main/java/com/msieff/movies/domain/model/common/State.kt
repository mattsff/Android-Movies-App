package com.msieff.movies.domain.model.common

/**
 * Generic sealed class to manage responses and statuses
 */

sealed class State<out T> {
    data class Loading<T>(var data: T? = null) : State<T>()
    data class Error<T>(var exception: Throwable) : State<T>()
    data class Data<T>(var data: T?) : State<T>()
}