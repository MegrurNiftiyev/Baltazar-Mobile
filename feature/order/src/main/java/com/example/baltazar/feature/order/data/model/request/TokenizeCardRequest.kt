package com.example.baltazar.feature.order.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenizeCardRequest(
    val cardNumber: String,
    val cardHolder: String,
    val expiryMonth: String,
    val expiryYear: String,
    val cvv: String
)
