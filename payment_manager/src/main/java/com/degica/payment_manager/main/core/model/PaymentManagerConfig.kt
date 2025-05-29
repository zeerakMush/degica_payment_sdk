package com.degica.payment_manager.main.core.model

data class PaymentManagerConfig(
    val apiKey: String,
    val customerIp: String? = null,
    val customerEmail: String? = null,
)