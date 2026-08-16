package com.example.baltazar.feature.rentacar.data.source.remote

import com.example.baltazar.feature.rentacar.data.model.dto.CarListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RentACarApi {
    @GET("api/services/rentacar/cars")
    suspend fun getCars(
        @Query("companyId") companyId: String? = null,
        @Query("brand") brand: String? = null,
        @Query("model") model: String? = null,
        @Query("category") category: String? = null,
        @Query("transmission") transmission: String? = null,
        @Query("fuelType") fuelType: String? = null,
        @Query("minPrice") minPrice: Double? = null,
        @Query("maxPrice") maxPrice: Double? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): CarListResponseDto
}
