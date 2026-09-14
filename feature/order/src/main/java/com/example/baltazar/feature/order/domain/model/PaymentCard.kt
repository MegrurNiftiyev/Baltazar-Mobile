package com.example.baltazar.feature.order.domain.model

data class PaymentCard(
    val id: String?,
    val paymentMethodId: String,
    val brand: String,
    val last4: String,
    val expiryMonth: Int,
    val expiryYear: Int
)
