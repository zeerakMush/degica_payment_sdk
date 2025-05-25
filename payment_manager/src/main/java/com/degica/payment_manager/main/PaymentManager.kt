package com.degica.payment_manager.main

import android.util.Log
import com.degica.payment_manager.main.api.model.FraudDetailsApiModel
import com.degica.payment_manager.main.api.model.PaymentDataApiModel
import com.degica.payment_manager.main.core.model.PaymentData
import com.degica.payment_manager.main.core.model.toMap
import com.degica.payment_manager.main.network.ApiCaller
import com.degica.payment_manager.main.network.ApiResult
import com.degica.payment_manager.main.network.RetrofitClient

class PaymentManager {
    private val apiCaller: ApiCaller = ApiCaller()

    companion object {
        fun createPaymentManager(): PaymentManager {
            return PaymentManager()
        }
    }

    suspend fun createPayment(paymentData: PaymentData): Boolean {
        // todo add repo layer here
        val request = PaymentDataApiModel(
            amount = paymentData.amount,
            currency = paymentData.currency.key,
            locale = paymentData.locale,
            paymentDetails = paymentData.paymentDetails.toMap(),
            capture = paymentData.capture,
            fraudDetails = FraudDetailsApiModel(
                customerIp = "54.199.14.70",
                customerEmail = "test@email.com"
            )
        )

        val response = apiCaller.call { RetrofitClient.api.createPayment(request) }
        Log.d("XDD", "createPayment: $response")
        return when(response){
            is ApiResult.ApiFailure -> false
            is ApiResult.ApiSuccess -> {
                Log.d("XDD", "createPayment: ${response.response}")
                true
            }
        }
    }
}


