package com.example.baltazar.feature.food.data.repository

import com.example.baltazar.core.core.exceptions.NetworkException
import com.example.baltazar.feature.food.data.source.remote.FoodApi
import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.food.domain.model.FoodItem
import com.example.baltazar.feature.food.domain.repository.IFoodRepository
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val api: FoodApi
) : IFoodRepository {

    override suspend fun getFoodItems(
        companyId: String?,
        category: String?,
        name: String?,
        minPrice: Double?,
        maxPrice: Double?,
        limit: Int?,
        cursor: String?
    ): Result<PaginatedList<FoodItem>> {
        return try {
            val response = executeRequest(
                apiCall = {
                    api.getFoodItems(
                        companyId = companyId,
                        category = category,
                        name = name,
                        minPrice = minPrice,
                        maxPrice = maxPrice,
                        limit = limit,
                        cursor = cursor
                    )
                }
            )
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
