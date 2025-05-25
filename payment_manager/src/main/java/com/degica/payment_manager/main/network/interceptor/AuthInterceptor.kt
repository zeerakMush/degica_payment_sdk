package com.degica.payment_manager.main.network.interceptor

import okhttp3.Interceptor
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthInterceptor() : Interceptor {
    @OptIn(ExperimentalEncodingApi::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .addHeader("authorization", "Basic ${Base64.Default.encode("sk_test_7ka9dg6g8854o2buom5lwmc1".encodeToByteArray())}")
            .build()
        return chain.proceed(request)
    }
}