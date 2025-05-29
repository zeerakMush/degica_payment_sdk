package com.degica.payment_manager.main.core

sealed class PaymentState {
    data class Authorized(
        val paymentId: String
    ) : PaymentState()

    data class Failed(
        val errorMessage: String
    ) : PaymentState()
}