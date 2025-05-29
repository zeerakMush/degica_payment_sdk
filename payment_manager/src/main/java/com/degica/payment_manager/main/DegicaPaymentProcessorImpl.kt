package com.degica.payment_manager.main

import com.degica.payment_manager.main.api.model.FraudDetailsApiModel
import com.degica.payment_manager.main.api.model.PaymentDataApiModel
import com.degica.payment_manager.main.core.DegicaPaymentProcessor
import com.degica.payment_manager.main.core.PaymentState
import com.degica.payment_manager.main.core.model.PaymentData
import com.degica.payment_manager.main.core.model.PaymentManagerConfig
import com.degica.payment_manager.main.core.model.toMap
import com.degica.payment_manager.main.network.ApiCaller
import com.degica.payment_manager.main.network.ApiResult
import com.degica.payment_manager.main.network.RetrofitClient

internal class DegicaPaymentProcessorImpl(
    private val apiCaller: ApiCaller,
    private val config: PaymentManagerConfig
) : DegicaPaymentProcessor {

    override suspend fun createPayment(paymentData: PaymentData): PaymentState {
        val request = PaymentDataApiModel(
            amount = paymentData.amount,
            currency = paymentData.currency.key,
            locale = paymentData.locale,
            paymentDetails = paymentData.paymentDetails.toMap(),
            capture = paymentData.capture,
            fraudDetails = FraudDetailsApiModel(
                customerIp = config.customerIp,
                customerEmail = config.customerEmail
            )
        )

        val response = apiCaller.call { RetrofitClient.api.createPayment(request) }
        return when (response) {
            is ApiResult.ApiFailure -> PaymentState.Failed(
                response.error?.message ?: "Unknown error"
            )

            is ApiResult.ApiSuccess -> PaymentState.Authorized(paymentId = response.response.id)
        }
    }

}