package com.example.baltazar.feature.hotel.data.model.request

import com.example.baltazar.feature.hotel.domain.model.HotelFilter
import com.example.baltazar.feature.hotel.domain.model.HotelRoomFilter
import kotlinx.serialization.Serializable

@Serializable
data class HotelFilterRequest(
    val city: String? = null,
    val name: String? = null,
    val starRating: Int? = null,
    val minRating: Double? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)

@Serializable
data class HotelRoomFilterRequest(
    val roomType: String? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)

fun HotelFilter.toRequest(): HotelFilterRequest = HotelFilterRequest(
    city = city,
    name = name,
    starRating = starRating,
    minRating = minRating,
    minPrice = minPrice,
    maxPrice = maxPrice,
    limit = limit,
    cursor = cursor
)

fun HotelRoomFilter.toRequest(): HotelRoomFilterRequest = HotelRoomFilterRequest(
    roomType = roomType,
    limit = limit,
    cursor = cursor
)
