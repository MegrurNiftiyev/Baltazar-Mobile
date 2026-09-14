package com.example.baltazar.feature.order.domain.model

data class PaymentTransaction(
    val id: String,
    val userId: String,
    val orderId: String,
    val paymentMethodId: String?,
    val amount: Double,
    val currency: String,
    val providerEventId: String?,
    val status: String,
    val createdAt: String
)
