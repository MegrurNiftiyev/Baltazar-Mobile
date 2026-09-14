package com.example.baltazar.feature.order.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProcessPaymentRequest(
    @SerialName("paymentMethodId") val paymentMethodId: String
)
