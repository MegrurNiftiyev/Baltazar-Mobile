package com.example.baltazar.feature.order.domain.repository

import com.example.baltazar.feature.order.domain.model.PaymentCard
import com.example.baltazar.feature.order.domain.model.PaymentTransaction
import com.example.baltazar.feature.order.domain.model.TokenizedCard

interface IPaymentRepository {
    suspend fun tokenizeCard(
        cardNumber: String,
        cardHolder: String,
        expiryMonth: String,
        expiryYear: String,
        cvv: String
    ): Result<TokenizedCard>

    suspend fun addPaymentMethod(
        paymentMethodId: String,
        brand: String,
        last4: String,
        expiryMonth: Int,
        expiryYear: Int
    ): Result<PaymentCard>

    suspend fun getAllCards(): Result<List<PaymentCard>>

    suspend fun processPayment(
        orderId: String,
        paymentMethodId: String
    ): Result<PaymentTransaction>
}
