package com.degica.payment_manager.main.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class ApiCaller(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun <T> call(api: suspend () -> Response<T>): ApiResult<T> {
        return withContext(dispatcher) { performCall(api) }
    }

    private suspend fun <T> performCall(
        api: suspend () -> Response<T>
    ): ApiResult<T> {
        return try {
            val response = api()
            if (response.isSuccessful) {
                val data = response.body() as T
                ApiResult.ApiSuccess(data)
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            ApiResult.ApiFailure(e)
        }
    }
}

sealed class ApiResult<T> {
    data class ApiSuccess<T>(val response: T) : ApiResult<T>()
    data class ApiFailure<T>(val throwable: Throwable) : ApiResult<T>()
}