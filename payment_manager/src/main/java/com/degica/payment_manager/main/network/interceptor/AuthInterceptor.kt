package com.degica.payment_manager.main.network.interceptor

import okhttp3.Interceptor
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthInterceptor(
    val apiKey: String
) : Interceptor {
    @OptIn(ExperimentalEncodingApi::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .addHeader("authorization", "Basic ${Base64.Default.encode(apiKey.encodeToByteArray())}")
            .build()
        return chain.proceed(request)
    }
}