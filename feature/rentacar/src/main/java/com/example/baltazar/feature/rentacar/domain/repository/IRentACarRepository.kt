package com.example.baltazar.feature.rentacar.domain.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.rentacar.domain.model.CarItem

interface IRentACarRepository {
    suspend fun getCars(
        companyId: String? = null,
        brand: String? = null,
        model: String? = null,
        category: String? = null,
        transmission: String? = null,
        fuelType: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): Result<PaginatedList<CarItem>>
}
