package com.example.baltazar.feature.hotel.data.model.dto

import com.example.baltazar.core.data.model.dto.PaginationDto
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.hotel.domain.model.HotelItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceRangeDto(
    @SerialName("min") val min: Double,
    @SerialName("max") val max: Double
)

@Serializable
data class HotelItemDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("starRating") val starRating: Int,
    @SerialName("city") val city: String,
    @SerialName("address") val address: String,
    @SerialName("amenities") val amenities: List<String> = emptyList(),
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("priceRange") val priceRange: PriceRangeDto,
    @SerialName("priceSuffix") val priceSuffix: String,
    @SerialName("currency") val currency: String = "AZN",
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("createdAt") val createdAt: String
) {
    fun toDomain(): HotelItem = HotelItem(
        id = id,
        title = title,
        name = name,
        description = description,
        starRating = starRating,
        city = city,
        address = address,
        amenities = amenities,
        rating = rating,
        reviewCount = reviewCount,
        minPrice = priceRange.min,
        maxPrice = priceRange.max,
        priceSuffix = priceSuffix,
        currency = currency,
        images = images,
        createdAt = createdAt
    )
}

@Serializable
data class HotelListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<HotelItemDto> = emptyList(),
    @SerialName("pagination") val pagination: PaginationDto
) {
    fun toDomain(): PaginatedList<HotelItem> = PaginatedList(
        items = data.map { it.toDomain() },
        pagination = pagination.toDomain()
    )
}
