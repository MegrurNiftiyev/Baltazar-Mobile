package com.example.baltazar.feature.hotel.domain.model

data class HotelItem(
    val id: String,
    val title: String,
    val name: String,
    val description: String,
    val starRating: Int,
    val city: String,
    val address: String,
    val amenities: List<String>,
    val rating: Double,
    val reviewCount: Int,
    val minPrice: Double,
    val maxPrice: Double,
    val priceSuffix: String,
    val currency: String,
    val images: List<String>,
    val createdAt: String
)


