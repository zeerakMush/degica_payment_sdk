package com.degica.payment_manager.main.core.model

open class PaymentDetails(
    val paymentType: PaymentType,
)


fun PaymentDetails.toMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    map["type"] = paymentType.key

    when (this) {
        is CreditCardPaymentDetails -> {
            map["number"] = this.number
            map["month"] = this.month
            map["year"] = this.year
            map["verification_value"] = this.cvv
            map["name"] = this.name
            map["email"] = this.email ?: "" // Optional field, handle nullability
        }
        else -> {
            // Handle other payment types if needed
        }
    }
    return map
}