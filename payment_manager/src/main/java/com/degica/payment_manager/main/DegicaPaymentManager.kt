package com.degica.payment_manager.main

import com.degica.payment_manager.main.core.DegicaPaymentProcessor
import com.degica.payment_manager.main.core.model.PaymentManagerConfig
import com.degica.payment_manager.main.network.ApiCaller

class DegicaPaymentManager private constructor(
    private val apiCaller: ApiCaller,
    internal val config: PaymentManagerConfig
) {
    val paymentProcessor: DegicaPaymentProcessor = DegicaPaymentProcessorImpl(apiCaller, config)

    companion object {
        @Volatile
        private var instance: DegicaPaymentManager? = null

        fun createPaymentManager(config: PaymentManagerConfig): DegicaPaymentManager {
            return instance ?: synchronized(this) {
                instance ?: DegicaPaymentManager(
                    apiCaller = ApiCaller(), config = config
                ).also { instance = it }
            }
        }

        fun getInstance(): DegicaPaymentManager? = instance
    }

}


