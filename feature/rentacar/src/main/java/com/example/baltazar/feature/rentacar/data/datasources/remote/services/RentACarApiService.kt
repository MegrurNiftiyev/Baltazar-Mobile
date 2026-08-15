package com.example.baltazar.feature.rentacar.data.datasources.remote.services

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.rentacar.data.model.dto.CarDetailDto
import com.example.baltazar.feature.rentacar.data.model.dto.CarDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RentACarApiService {

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
    ): PaginatedResponse<CarDto>

    @GET("api/services/rentacar/cars/{id}")
    suspend fun getCarDetails(
        @Path("id") id: String
    ): ApiResponse<CarDetailDto>
}
