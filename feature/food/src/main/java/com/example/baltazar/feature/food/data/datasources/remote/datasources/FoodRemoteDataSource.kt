package com.example.baltazar.feature.food.data.datasources.remote.datasources

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.feature.food.data.datasources.remote.services.FoodApiService
import com.example.baltazar.feature.food.data.model.response.FoodDetailResponse
import com.example.baltazar.feature.food.data.model.response.FoodResponse
import javax.inject.Inject

class FoodRemoteDataSource @Inject constructor(
    private val apiService: FoodApiService
) {
    suspend fun getFoodItems(
        companyId: String? = null,
        category: String? = null,
        name: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): FoodResponse {
        return executeRequest(
            apiCall = {
                apiService.getFoodItems(
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
    }

    suspend fun getFoodItemDetails(id: String): FoodDetailResponse {
        return executeRequest(
            apiCall = {
                apiService.getFoodItemDetails(id)
            }
        )
    }
}
