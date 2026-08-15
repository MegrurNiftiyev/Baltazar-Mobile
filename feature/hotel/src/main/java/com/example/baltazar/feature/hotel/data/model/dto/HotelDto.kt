package com.example.baltazar.feature.hotel.data.model.dto

import com.example.baltazar.feature.hotel.domain.model.HotelItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class HotelDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("starRating") val starRating: Int = 0,
    @SerialName("city") val city: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("amenities") val amenities: List<String> = emptyList(),
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("priceRange") val priceRange: PriceRangeDto? = null,
    @SerialName("priceSuffix") val priceSuffix: String? = "/ night",
    @SerialName("currency") val currency: String? = "AZN",
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("createdAt") val createdAt: String? = null
) {
    fun toDomain(): HotelItem {
        return HotelItem(
            id = id,
            title = title ?: name.orEmpty(),
            name = name ?: title.orEmpty(),
            description = description.orEmpty(),
            starRating = starRating,
            city = city.orEmpty(),
            address = address.orEmpty(),
            amenities = amenities,
            rating = rating,
            reviewCount = reviewCount,
            minPrice = priceRange?.min ?: 0.0,
            maxPrice = priceRange?.max ?: 0.0,
            priceSuffix = priceSuffix ?: "/ night",
            currency = currency ?: "AZN",
            images = images,
            createdAt = createdAt.orEmpty()
        )
    }
}
