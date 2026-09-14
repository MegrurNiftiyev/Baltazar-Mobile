package com.example.baltazar.feature.order.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCardRequest(
    @SerialName("paymentMethodId") val paymentMethodId: String,
    @SerialName("brand") val brand: String,
    @SerialName("last4") val last4: String,
    @SerialName("expiryMonth") val expiryMonth: Int,
    @SerialName("expiryYear") val expiryYear: Int
)
