package com.degica.payment_manager

import com.degica.payment_manager.main.DegicaPaymentProcessorImpl
import com.degica.payment_manager.main.api.model.ApiError
import com.degica.payment_manager.main.api.model.CreatePaymentResponse
import com.degica.payment_manager.main.core.DegicaPaymentProcessor
import com.degica.payment_manager.main.core.PaymentState
import com.degica.payment_manager.main.core.model.Currency
import com.degica.payment_manager.main.core.model.PaymentData
import com.degica.payment_manager.main.core.model.PaymentDetails
import com.degica.payment_manager.main.core.model.PaymentManagerConfig
import com.degica.payment_manager.main.core.model.PaymentType
import com.degica.payment_manager.main.network.ApiCaller
import com.degica.payment_manager.main.network.ApiResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class DegicaPaymentProcessorImplTest {
    private lateinit var paymentProcessor: DegicaPaymentProcessor

    @MockK
    lateinit var apiCaller: ApiCaller

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        paymentProcessor = DegicaPaymentProcessorImpl(
            apiCaller = apiCaller,
            config = PaymentManagerConfig(
                customerIp = "", apiKey = ""
            )
        )
    }

    @Test
    fun createPayment_returnsAuthorizedState_onApiSuccess() = runBlocking {
        val response = CreatePaymentResponse(status = "Success", id = "payment123")
        coEvery { apiCaller.call(any<suspend () -> Response<CreatePaymentResponse>>()) } returns
                ApiResult.ApiSuccess(response)


        val result = paymentProcessor.createPayment(paymentData)

        assertEquals(PaymentState.Authorized("payment123"), result)
    }

    @Test
    fun createPayment_returnsFailedState_onApiFailureWithMessage() = runBlocking {
        coEvery { apiCaller.call(any<suspend () -> Response<CreatePaymentResponse>>()) } returns
                ApiResult.ApiFailure(
                    Exception("Network error"),
                    ApiError(code = "NetworkError", message = "Network error")
                )

        val result = paymentProcessor.createPayment(paymentData)

        assertEquals(PaymentState.Failed("Network error"), result)
    }

    @Test
    fun createPayment_returnsFailedState_onApiFailureWithNullMessage() = runBlocking {
        coEvery { apiCaller.call(any<suspend () -> Response<CreatePaymentResponse>>()) } returns
                ApiResult.ApiFailure(Exception(""), null)

        val result = paymentProcessor.createPayment(paymentData)

        assertEquals(PaymentState.Failed("Unknown error"), result)
    }

    private val paymentData = PaymentData(
        amount = 1000,
        currency = Currency.USD,
        locale = "en_US",
        paymentDetails = PaymentDetails(PaymentType.CREDIT_CARD),
        capture = true
    )
}