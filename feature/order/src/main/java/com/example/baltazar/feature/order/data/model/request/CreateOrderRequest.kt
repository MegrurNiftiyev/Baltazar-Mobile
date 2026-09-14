package com.example.baltazar.feature.order.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequest(
    @SerialName("serviceType") val serviceType: String,
    @SerialName("serviceId") val serviceId: String,
    @SerialName("subItemId") val subItemId: String? = null
)
