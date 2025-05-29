package com.degica.payment_manager.main.core

import com.degica.payment_manager.main.core.model.PaymentData

interface DegicaPaymentProcessor {
    suspend fun createPayment(paymentData: PaymentData): PaymentState
}