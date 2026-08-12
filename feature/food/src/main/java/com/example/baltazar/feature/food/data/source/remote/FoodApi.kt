package com.example.baltazar.feature.food.data.source.remote

import com.example.baltazar.feature.food.data.model.dto.FoodListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("api/services/food/items")
    suspend fun getFoodItems(
        @Query("companyId") companyId: String? = null,
        @Query("category") category: String? = null,
        @Query("name") name: String? = null,
        @Query("minPrice") minPrice: Double? = null,
        @Query("maxPrice") maxPrice: Double? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): FoodListResponseDto
}
