package com.example.baltazar.feature.travel.domain.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.travel.domain.model.TourItem

interface ITravelRepository {
    suspend fun getTours(
        companyId: String? = null,
        category: String? = null,
        title: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        minRating: Double? = null,
        startDate: String? = null,
        endDate: String? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): Result<PaginatedList<TourItem>>
}
