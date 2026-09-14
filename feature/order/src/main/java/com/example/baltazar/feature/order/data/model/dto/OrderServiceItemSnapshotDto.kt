package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.feature.order.domain.model.OrderServiceItemSnapshot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderServiceItemSnapshotDto(
    @SerialName("title") val title: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("priceSuffix") val priceSuffix: String? = null,
    @SerialName("currency") val currency: String? = null
) {
    fun toDomain(): OrderServiceItemSnapshot {
        return OrderServiceItemSnapshot(
            title = title.orEmpty(),
            image = image,
            price = price ?: 0.0,
            priceSuffix = priceSuffix,
            currency = currency.orEmpty()
        )
    }
}
