package com.degica.payment_manager.main.core.model


data class CreditCardPaymentDetails(
    val number: String,
    val name: String,
    val month: String,
    val year: String,
    val cvv: String,
    val email: String? = null
) : PaymentDetails(PaymentType.CREDIT_CARD)