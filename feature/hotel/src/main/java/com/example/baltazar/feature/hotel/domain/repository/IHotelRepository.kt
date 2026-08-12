package com.example.baltazar.feature.hotel.domain.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.hotel.domain.model.HotelItem

interface IHotelRepository {
    suspend fun getHotels(
        city: String? = null,
        name: String? = null,
        starRating: Int? = null,
        minRating: Double? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): Result<PaginatedList<HotelItem>>
}
