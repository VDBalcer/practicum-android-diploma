package ru.practicum.android.diploma.data.network

import java.io.IOException

sealed class NetworkResult<out T> {

    data class Success<T>(
        val data: T
    ) : NetworkResult<T>()

    data class Error(
        val code: Int,
        val message: String? = null
    ) : NetworkResult<Nothing>()

    data class NetworkError(
        val throwable: IOException
    ) : NetworkResult<Nothing>()
}
