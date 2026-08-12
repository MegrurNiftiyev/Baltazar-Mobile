package com.example.baltazar.feature.hotel.data.source.remote

import com.example.baltazar.feature.hotel.data.model.dto.HotelListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HotelApi {
    @GET("api/services/hotel")
    suspend fun getHotels(
        @Query("city") city: String? = null,
        @Query("name") name: String? = null,
        @Query("starRating") starRating: Int? = null,
        @Query("minRating") minRating: Double? = null,
        @Query("minPrice") minPrice: Double? = null,
        @Query("maxPrice") maxPrice: Double? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): HotelListResponseDto
}
