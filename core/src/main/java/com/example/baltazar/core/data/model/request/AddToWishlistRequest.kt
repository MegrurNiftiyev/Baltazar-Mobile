package com.example.baltazar.core.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddToWishlistRequest(
    @SerialName("serviceId") val serviceId: String,
    @SerialName("serviceType") val serviceType: String
)
