package com.degica.payment_manager.main.network

import com.degica.payment_manager.main.DegicaPaymentManager
import com.degica.payment_manager.main.network.gateway.PaymentManagerGateway
import com.degica.payment_manager.main.network.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://komoju.com/api/"
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private fun  httpClient(apiKey: String) = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(apiKey))
        .addInterceptor(logging)
        .build()

    private fun getRetrofit(apiKey: String): PaymentManagerGateway {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient(apiKey))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PaymentManagerGateway::class.java)
    }

    internal val api: PaymentManagerGateway by lazy {
        getRetrofit(DegicaPaymentManager.getInstance()?.config?.apiKey ?: throw IllegalStateException("PaymentManager not initialized"))
    }


}