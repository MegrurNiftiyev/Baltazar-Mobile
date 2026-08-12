package com.example.baltazar.feature.travel.data.source.remote

import com.example.baltazar.feature.travel.data.model.dto.TourListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TravelApi {
    @GET("api/services/travel/tours")
    suspend fun getTours(
        @Query("companyId") companyId: String? = null,
        @Query("category") category: String? = null,
        @Query("title") title: String? = null,
        @Query("minPrice") minPrice: Double? = null,
        @Query("maxPrice") maxPrice: Double? = null,
        @Query("minRating") minRating: Double? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): TourListResponseDto
}
