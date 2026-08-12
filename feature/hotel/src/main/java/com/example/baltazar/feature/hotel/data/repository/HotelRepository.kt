package com.example.baltazar.feature.hotel.data.repository

import com.example.baltazar.core.core.exceptions.NetworkException
import com.example.baltazar.feature.hotel.data.source.remote.HotelApi
import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.hotel.domain.model.HotelItem
import com.example.baltazar.feature.hotel.domain.repository.IHotelRepository
import javax.inject.Inject

class HotelRepository @Inject constructor(
    private val api: HotelApi
) : IHotelRepository {

    override suspend fun getHotels(
        city: String?,
        name: String?,
        starRating: Int?,
        minRating: Double?,
        minPrice: Double?,
        maxPrice: Double?,
        limit: Int?,
        cursor: String?
    ): Result<PaginatedList<HotelItem>> {
        return try {
            val response = executeRequest(
                apiCall = {
                    api.getHotels(
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
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
