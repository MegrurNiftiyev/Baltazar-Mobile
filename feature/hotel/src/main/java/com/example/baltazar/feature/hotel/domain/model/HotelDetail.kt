package com.example.baltazar.feature.hotel.domain.model

import com.example.baltazar.core.domain.model.ReviewEligibility

data class HotelDetail(
    val id: String = "",
    val name: String = "",
    val about: String = "",
    val city: String = "",
    val address: String = "",
    val starRating: Int = 0,
    val images: List<String> = emptyList(),
    val serviceType: String = "",
    val status: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val priceSuffix: String = "/ gecə",
    val minPrice: Double = 0.0,
    val maxPrice: Double = 0.0,
    val amenities: List<String> = emptyList(),
    val reviewEligibility: ReviewEligibility = ReviewEligibility()
)
