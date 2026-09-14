package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.feature.order.domain.model.DeliveryAddress
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryAddressDto(
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lng") val lng: Double? = null,
    @SerialName("addressName") val addressName: String? = null
) {
    fun toDomain(): DeliveryAddress {
        return DeliveryAddress(
            lat = lat ?: 0.0,
            lng = lng ?: 0.0,
            addressName = addressName.orEmpty()
        )
    }
}
