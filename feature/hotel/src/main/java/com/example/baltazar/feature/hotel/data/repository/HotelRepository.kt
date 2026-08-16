package com.example.baltazar.feature.hotel.data.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.PaginationInfo
import com.example.baltazar.feature.hotel.data.datasources.remote.datasources.HotelRemoteDataSource
import com.example.baltazar.feature.hotel.domain.model.HotelDetail
import com.example.baltazar.feature.hotel.domain.model.HotelItem
import com.example.baltazar.feature.hotel.domain.model.HotelRoom
import com.example.baltazar.feature.hotel.domain.repository.IHotelRepository
import javax.inject.Inject

class HotelRepository @Inject constructor(
    private val remoteDataSource: HotelRemoteDataSource
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
            val response = remoteDataSource.getHotels(
                city = city,
                name = name,
                starRating = starRating,
                minRating = minRating,
                minPrice = minPrice,
                maxPrice = maxPrice,
                limit = limit,
                cursor = cursor
            )
            val domainItems = response.data.map { it.toDomain() }
            val paginationInfo = response.pagination?.toDomain() ?: PaginationInfo(
                nextCursor = null,
                hasMore = false,
                limit = limit ?: 20
            )

            Result.success(
                PaginatedList(
                    items = domainItems,
                    pagination = paginationInfo
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHotelDetail(id: String): Result<HotelDetail> {
        return try {
            val response = remoteDataSource.getHotelDetails(id)
            val detail = response.data?.toDomain() ?: throw Exception("Hotel not found")
            Result.success(detail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHotelRooms(
        id: String,
        roomType: String?,
        limit: Int?,
        cursor: String?
    ): Result<List<HotelRoom>> {
        return try {
            val response = remoteDataSource.getHotelRooms(
                id = id,
                roomType = roomType,
                limit = limit,
                cursor = cursor
            )
            val rooms = response.data?.map { it.toDomain() } ?: emptyList()
            Result.success(rooms)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
