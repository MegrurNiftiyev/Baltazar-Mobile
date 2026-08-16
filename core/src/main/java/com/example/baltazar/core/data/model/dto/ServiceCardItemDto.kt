package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.domain.model.ServiceCardItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceCardItemDto(
    @SerialName("id") val id: String,
    @SerialName("serviceType") val serviceType: ServiceType,
    @SerialName("serviceId") val serviceId: String,
    @SerialName("title") val title: String,
    @SerialName("image") val image: String,
    @SerialName("price") val price: Double,
    @SerialName("priceSuffix") val priceSuffix: String = "",
    @SerialName("currency") val currency: String = "AZN",
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("ratingCount") val ratingCount: Int = 0,
    @SerialName("category") val category: String? = null
) {
    fun toDomain(): ServiceCardItem = ServiceCardItem(
        id = id,
        serviceType = serviceType,
        serviceId = serviceId,
        title = title,
        image = image,
        price = price,
        priceSuffix = priceSuffix,
        currency = currency,
        rating = rating,
        ratingCount = ratingCount,
        category = category
    )
}
