package com.degica.payment_manager.main.api.model

import com.google.gson.annotations.SerializedName

data class PaymentDataApiModel(
    val capture: Boolean,
    val currency: String,
    val locale: String,
    @SerializedName("payment_details")
    val paymentDetails: Map<String, Any>,
    val amount: Int,
    @SerializedName("fraud_details")
    val fraudDetails: FraudDetailsApiModel
)