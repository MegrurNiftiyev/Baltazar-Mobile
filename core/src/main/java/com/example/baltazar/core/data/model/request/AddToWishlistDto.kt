package com.example.baltazar.core.data.model.request

import com.example.baltazar.core.domain.model.AddToWishlistInfo
import com.example.baltazar.core.enums.ServiceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddToWishlistDto(
    @SerialName("serviceId") val serviceId: String,
    @SerialName("serviceType") val serviceType: ServiceType
)

fun AddToWishlistInfo.toDto(): AddToWishlistDto {
    return AddToWishlistDto(
        serviceId = serviceId,
        serviceType = serviceType
    )
}
