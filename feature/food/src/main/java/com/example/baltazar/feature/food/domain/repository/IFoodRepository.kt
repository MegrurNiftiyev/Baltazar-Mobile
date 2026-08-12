package com.example.baltazar.feature.food.domain.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.food.domain.model.FoodItem

interface IFoodRepository {
    suspend fun getFoodItems(
        companyId: String? = null,
        category: String? = null,
        name: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): Result<PaginatedList<FoodItem>>
}
