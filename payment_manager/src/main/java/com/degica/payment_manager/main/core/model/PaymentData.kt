package com.degica.payment_manager.main.core.model

data class PaymentData(
    val amount: Int,
    val currency: Currency,
    val paymentDetails: PaymentDetails,
    val locale: String = "ja",
    val returnUrl: String? = null,
    val capture: Boolean = false,
    val tax: Int = 0,
    val externalOrderNum: String? = null,
    val metaData: Map<String, String> = emptyMap()
)

