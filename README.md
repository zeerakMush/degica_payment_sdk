# Payment Manager API

A Kotlin-based library for managing payments, supporting multiple payment types and currencies. It
provides a simple interface to create payments and handle payment states using Retrofit for network
communication.

## Features

- Create payments with various payment types (e.g., credit card)
- Supports multiple currencies (USD, JPY)
- Handles payment states (Authorized, Failed)
- Easily extendable for new payment methods

## Setup Instructions

Add it in your `settings.gradle.kts` at the end of repositories:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Step 2. Add the dependency

```kotlin
dependencies {
    implementation("com.github.zeerakMush:degica_payment_sdk:TAG")
}
```

### Usage Example

```kotlin
val paymentManager = PaymentManager.createPaymentManager()

val paymentData = PaymentData(
    amount = 1000,
    currency = Currency.USD,
    locale = "en_US",
    paymentDetails = CreditCardPaymentDetails(
        number = "4123111111111000",
        month = "01",
        year = "2028",
        cvv = "123",
        name = "Taro Yamada",
        email = "test@example.com"
    ),
    capture = true
)

suspend fun makePayment() {
    when (val result = paymentManager.createPayment(paymentData)) {
        is PaymentState.Authorized -> println("Payment authorized: ${result.paymentId}")
        is PaymentState.Failed -> println("Payment failed: ${result.reason}")
    }
}
```

## Improvements
### Error Handling Enhancements

- Provide more granular error types in `PaymentState.Failed` (e.g., network error, validation error, server error).
- Add error codes to `PaymentState.Failed` for easier client-side handling.

### Configuration Flexibility

- Allow dynamic configuration of API endpoints and headers (e.g., via a config file or builder pattern).
- Support runtime token/header updates for authentication.

### Logging and Monitoring

- Integrate structured logging for all API calls and responses.
- Add hooks or callbacks for monitoring payment status changes.

### Extensibility

- Use interfaces or sealed classes for payment methods to make it easier to add new types (e.g., PayPal, Apple Pay).
- Provide a plugin mechanism for custom fraud checks or payment processors.

### Testing

- Increase test coverage, especially for edge cases and error scenarios.
- Add integration tests with a mock server.

### Security

- Mask sensitive data (e.g., card numbers) in logs.
- Validate all input data before sending requests.

### CI/CD Integration

- Add GitHub Actions or another CI pipeline for automated testing and linting.