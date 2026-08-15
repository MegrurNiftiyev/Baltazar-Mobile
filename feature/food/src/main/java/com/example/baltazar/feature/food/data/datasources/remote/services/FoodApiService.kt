package com.example.baltazar.feature.food.data.datasources.remote.services

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.food.data.model.dto.FoodDetailDto
import com.example.baltazar.feature.food.data.model.dto.FoodItemDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodApiService {

    @GET("api/services/food/items")
    suspend fun getFoodItems(
        @Query("companyId") companyId: String? = null,
        @Query("category") category: String? = null,
        @Query("name") name: String? = null,
        @Query("minPrice") minPrice: Double? = null,
        @Query("maxPrice") maxPrice: Double? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): PaginatedResponse<FoodItemDto>

    @GET("api/services/food/items/{id}")
    suspend fun getFoodItemDetails(
        @Path("id") id: String
    ): ApiResponse<FoodDetailDto>
}
