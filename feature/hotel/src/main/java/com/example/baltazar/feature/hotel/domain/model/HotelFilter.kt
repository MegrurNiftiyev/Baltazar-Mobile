package com.example.baltazar.feature.hotel.domain.model

data class HotelFilter(
    val city: String? = null,
    val name: String? = null,
    val starRating: Int? = null,
    val minRating: Double? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)

data class HotelRoomFilter(
    val roomType: String? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)
