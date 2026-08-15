package com.example.baltazar.feature.hotel.data.model.dto

import com.example.baltazar.core.data.model.dto.ReviewEligibilityDto
import com.example.baltazar.core.domain.model.ReviewEligibility
import com.example.baltazar.feature.hotel.domain.model.HotelDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotelDetailDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String? = null,
    @SerialName("about") val about: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("starRating") val starRating: Int = 0,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("serviceType") val serviceType: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("priceSuffix") val priceSuffix: String? = "/ gecə",
    @SerialName("priceRange") val priceRange: PriceRangeDto? = null,
    @SerialName("amenities") val amenities: List<String> = emptyList(),
    @SerialName("reviewEligibility") val reviewEligibility: ReviewEligibilityDto? = null
) {
    fun toDomain(): HotelDetail = HotelDetail(
        id = id,
        name = name.orEmpty(),
        about = about.orEmpty(),
        city = city.orEmpty(),
        address = address.orEmpty(),
        starRating = starRating,
        images = images,
        serviceType = serviceType.orEmpty(),
        status = status.orEmpty(),
        rating = rating,
        reviewCount = reviewCount,
        priceSuffix = if (priceSuffix.isNullOrBlank()) "/ gecə" else priceSuffix,
        minPrice = priceRange?.min ?: 0.0,
        maxPrice = priceRange?.max ?: 0.0,
        amenities = amenities,
        reviewEligibility = reviewEligibility?.toDomain() ?: ReviewEligibility()
    )
}
