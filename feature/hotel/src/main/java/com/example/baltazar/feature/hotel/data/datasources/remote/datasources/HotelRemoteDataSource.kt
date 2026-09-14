package com.example.baltazar.feature.hotel.data.datasources.remote.datasources

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.hotel.data.datasources.remote.services.HotelApiService
import com.example.baltazar.feature.hotel.data.model.dto.HotelDetailDto
import com.example.baltazar.feature.hotel.data.model.dto.HotelDto
import com.example.baltazar.feature.hotel.data.model.dto.HotelRoomDto
import javax.inject.Inject

class HotelRemoteDataSource @Inject constructor(
    private val apiService: HotelApiService
) {
    suspend fun getHotels(
        city: String? = null,
        name: String? = null,
        starRating: Int? = null,
        minRating: Double? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): PaginatedResponse<HotelDto> {
        return executeRequest(
            apiCall = {
                apiService.getHotels(
                    city = city,
                    name = name,
                    starRating = starRating,
                    minRating = minRating,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    limit = limit,
                    cursor = cursor
                )
            }
        )
    }

    suspend fun getHotelDetails(id: String): ApiResponse<HotelDetailDto> {
        return executeRequest(
            apiCall = {
                apiService.getHotelDetails(id)
            }
        )
    }

    suspend fun getHotelRooms(
        id: String,
        roomType: String? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): ApiResponse<List<HotelRoomDto>> {
        return executeRequest(
            apiCall = {
                apiService.getHotelRooms(
                    id = id,
                    roomType = roomType,
                    limit = limit,
                    cursor = cursor
                )
            }
        )
    }
}
