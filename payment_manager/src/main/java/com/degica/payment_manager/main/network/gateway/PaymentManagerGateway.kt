package com.degica.payment_manager.main.network.gateway

import com.degica.payment_manager.main.api.model.CreatePaymentResponse
import com.degica.payment_manager.main.api.model.PaymentDataApiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface PaymentManagerGateway {
    @POST("v1/payments")
    suspend fun createPayment(@Body paymentData: PaymentDataApiModel): Response<CreatePaymentResponse>
}