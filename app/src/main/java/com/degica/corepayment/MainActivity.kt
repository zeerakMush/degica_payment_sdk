package com.degica.corepayment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.degica.corepayment.databinding.ActivityMainBinding
import com.degica.payment_manager.main.DegicaPaymentManager
import com.degica.payment_manager.main.core.PaymentState
import com.degica.payment_manager.main.core.model.CreditCardPaymentDetails
import com.degica.payment_manager.main.core.model.Currency
import com.degica.payment_manager.main.core.model.PaymentData
import com.degica.payment_manager.main.core.model.PaymentManagerConfig
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener { view ->
            lifecycleScope.launch {
                callApi()
            }
        }
    }

    private suspend fun callApi() {
        val paymentManager = DegicaPaymentManager.createPaymentManager(
            config = PaymentManagerConfig(
                apiKey = "sk_test_7ka9dg6g8854o2buom5lwmc1",
                customerIp = "54.199.14.70",
                customerEmail = "test@email.com"
            )
        )

        val paymentResponse = paymentManager.paymentProcessor.createPayment(
            PaymentData(
                amount = 1,
                currency = Currency.JPY,
                paymentDetails = CreditCardPaymentDetails(
                    number = "4111111111111111",
                    name = "Taro Yamada",
                    month = "1",
                    year = "2028",
                    cvv = "123",
                    email = "test@example.com"
                ),
            )
        )

        when(paymentResponse){
            is PaymentState.Authorized -> Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            is PaymentState.Failed -> Toast.makeText(this, paymentResponse.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}