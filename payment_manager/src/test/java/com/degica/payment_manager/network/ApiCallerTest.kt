package com.degica.payment_manager.network

import com.degica.payment_manager.main.network.ApiCaller
import com.degica.payment_manager.main.network.ApiResult
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ApiCallerTest {
    private val apiCaller = ApiCaller()

    @Test
    fun call_returnsApiSuccess_whenResponseIsSuccessful() = runBlocking {
        val response = Response.success("data")
        val api: suspend () -> Response<String> = { response }

        val result = apiCaller.call(api)

        assertTrue(result is ApiResult.ApiSuccess)
        assertEquals("data", (result as ApiResult.ApiSuccess).response)
    }

    @Test
    fun call_returnsApiFailure_withParsedError_whenResponseIsError() = runBlocking {
        val errorJson = """{{"code":"401","message":"Unauthorized"}}"""
        val errorBody = ResponseBody.create("application/json".toMediaTypeOrNull(), errorJson)
        val response = Response.error<String>(401, errorBody)
        val api: suspend () -> Response<String> = { response }

        val result = apiCaller.call(api)

        assertTrue(result is ApiResult.ApiFailure)
        val error = (result as ApiResult.ApiFailure).error
        assertEquals("Unknown", error?.code)
    }

    @Test
    fun call_returnsApiFailure_withUnknownError_whenExceptionThrown() = runBlocking {
        val api: suspend () -> Response<String> = { throw RuntimeException("Something went wrong") }

        val result = apiCaller.call(api)

        assertTrue(result is ApiResult.ApiFailure)
        val error = (result as ApiResult.ApiFailure).error
        assertEquals("Unknown", error?.code)
        assertEquals("Something went wrong", error?.message)
    }

    @Test
    fun call_returnsApiFailure_withUnknownError_whenExceptionMessageIsNull() = runBlocking {
        val api: suspend () -> Response<String> = { throw RuntimeException(null as String?) }

        val result = apiCaller.call(api)

        assertTrue(result is ApiResult.ApiFailure)
        val error = (result as ApiResult.ApiFailure).error
        assertEquals("Unknown", error?.code)
        assertEquals("Unknown error", error?.message)
    }
}