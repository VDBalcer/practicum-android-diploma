package ru.practicum.android.diploma.data.network

sealed class NetworkResult<out T> {

    data class Success<T>(
        val data: T
    ) : NetworkResult<T>()

    data class Error(
        val code: Int,
        val message: String? = null
    ) : NetworkResult<Nothing>()

    object NetworkError : NetworkResult<Nothing>()
}
