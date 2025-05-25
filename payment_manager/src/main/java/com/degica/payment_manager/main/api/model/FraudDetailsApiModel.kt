package com.degica.payment_manager.main.api.model

import com.google.gson.annotations.SerializedName

data class FraudDetailsApiModel (
    @SerializedName("customer_ip")
    val customerIp: String,
    @SerializedName("customer_email")
    val customerEmail: String
)