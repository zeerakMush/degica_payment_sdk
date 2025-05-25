package com.degica.payment_manager.main

import com.degica.payment_manager.main.api.model.FraudDetailsApiModel
import com.degica.payment_manager.main.api.model.PaymentDataApiModel
import com.degica.payment_manager.main.core.PaymentState
import com.degica.payment_manager.main.core.model.PaymentData
import com.degica.payment_manager.main.core.model.toMap
import com.degica.payment_manager.main.network.ApiCaller
import com.degica.payment_manager.main.network.ApiResult
import com.degica.payment_manager.main.network.RetrofitClient

class PaymentManager(private val apiCaller: ApiCaller) {

    companion object {
        fun createPaymentManager(): PaymentManager {
            return PaymentManager(apiCaller = ApiCaller())
        }
    }

    suspend fun createPayment(paymentData: PaymentData): PaymentState {
        // todo add repo layer here

        val request = PaymentDataApiModel(
            amount = paymentData.amount,
            currency = paymentData.currency.key,
            locale = paymentData.locale,
            paymentDetails = paymentData.paymentDetails.toMap(),
            capture = paymentData.capture,
            fraudDetails = FraudDetailsApiModel( //todo: remove it and move to some config(it should not be passed with every request)
                customerIp = "54.199.14.70",
                customerEmail = "test@email.com"
            )
        )

        val response = apiCaller.call { RetrofitClient.api.createPayment(request) }
        return when(response){
            is ApiResult.ApiFailure -> PaymentState.Failed(response.error?.message ?: "Unknown error")
            is ApiResult.ApiSuccess -> PaymentState.Authorized(paymentId = response.response.id)
        }
    }
}


