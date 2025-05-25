package com.degica.payment_manager.main.network

import com.degica.payment_manager.main.api.model.ApiError
import com.degica.payment_manager.main.api.model.ErrorBody
import com.google.gson.GsonBuilder
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
                ApiResult.ApiFailure(
                    throwable = HttpException(response),
                    error = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), ErrorBody::class.java)
                        .toApiError()
                )
            }
        } catch (e: Exception) {
            ApiResult.ApiFailure(e, ApiError(code = "Unknown", message = e.message ?: "Unknown error"))
        }
    }
}

sealed class ApiResult<T> {
    internal data class ApiSuccess<T>(val response: T) : ApiResult<T>()
    internal data class ApiFailure<T>(val throwable: Throwable, val error: ApiError?) : ApiResult<T>()
}