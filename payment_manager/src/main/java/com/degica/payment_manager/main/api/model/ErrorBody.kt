package com.degica.payment_manager.main.api.model

internal data class ApiError(
    val code: String,
    val message: String,
    val param: String? = null,
    val details: Map<String, Any>? = null
)

internal data class ErrorBody(
    val error: ApiError
) {
    fun toApiError(): ApiError {
        return ApiError(
            code = error.code,
            message = error.message,
            param = error.param,
            details = error.details
        )
    }
}