package com.example.baltazar.feature.company.data.model.dto

import com.example.baltazar.feature.company.domain.model.RelatedItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatedItemDto(
    @SerialName("id") val id: String? = null,
    @SerialName("_id") val mongoId: String? = null,
    @SerialName("serviceType") val serviceType: String? = null,
    @SerialName("serviceId") val serviceId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("priceSuffix") val priceSuffix: String? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("ratingCount") val ratingCount: Int? = null,
    @SerialName("reviewCount") val reviewCount: Int? = null,
    @SerialName("category") val category: String? = null
) {
    fun toDomain(): RelatedItem {
        return RelatedItem(
            id = id ?: serviceId ?: mongoId ?: "",
            title = title ?: name.orEmpty(),
            imageUrl = image ?: imageUrl,
            price = price ?: 0.0,
            rating = rating ?: 0.0,
            category = category,
            priceSuffix = priceSuffix,
            currency = currency ?: "AZN",
            reviewCount = ratingCount ?: reviewCount ?: 0
        )
    }
}
