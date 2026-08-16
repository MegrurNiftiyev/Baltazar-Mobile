package com.example.baltazar.feature.hotel.data.datasources.remote.services

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.hotel.data.model.dto.HotelDetailDto
import com.example.baltazar.feature.hotel.data.model.dto.HotelDto
import com.example.baltazar.feature.hotel.data.model.dto.HotelRoomDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HotelApiService {

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
    ): PaginatedResponse<HotelDto>

    @GET("api/services/hotel/{id}")
    suspend fun getHotelDetails(
        @Path("id") id: String
    ): ApiResponse<HotelDetailDto>

    @GET("api/services/hotel/{id}/rooms")
    suspend fun getHotelRooms(
        @Path("id") id: String,
        @Query("roomType") roomType: String? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): ApiResponse<List<HotelRoomDto>>
}
