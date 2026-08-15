package com.example.baltazar.feature.travel.data.datasources.remote.services

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.travel.data.model.dto.TourDetailDto
import com.example.baltazar.feature.travel.data.model.dto.TourDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApiService {

    @GET("api/services/travel/tours")
    suspend fun getTours(
        @Query("companyId") companyId: String? = null,
        @Query("category") category: String? = null,
        @Query("minRating") minRating: Double? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("name") name: String? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): PaginatedResponse<TourDto>

    @GET("api/services/travel/tours/{id}")
    suspend fun getTourDetails(
        @Path("id") id: String
    ): ApiResponse<TourDetailDto>
}
