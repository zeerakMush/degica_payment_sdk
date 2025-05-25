package com.degica.corepayment

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.degica.corepayment.databinding.ActivityMainBinding
import com.degica.payment_manager.main.PaymentManager
import com.degica.payment_manager.main.core.model.CreditCardPaymentDetails
import com.degica.payment_manager.main.core.model.Currency
import com.degica.payment_manager.main.core.model.PaymentData
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
        PaymentManager.createPaymentManager().createPayment(
            PaymentData(
                amount = 1,
                currency = Currency.YEN,
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
    }
}