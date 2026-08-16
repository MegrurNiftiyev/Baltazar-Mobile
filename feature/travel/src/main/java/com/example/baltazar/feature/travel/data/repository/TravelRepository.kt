package com.example.baltazar.feature.travel.data.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.PaginationInfo
import com.example.baltazar.feature.travel.data.datasources.remote.datasources.TravelRemoteDataSource
import com.example.baltazar.feature.travel.domain.model.TourDetail
import com.example.baltazar.feature.travel.domain.model.TourItem
import com.example.baltazar.feature.travel.domain.repository.ITravelRepository
import javax.inject.Inject

class TravelRepository @Inject constructor(
    private val remoteDataSource: TravelRemoteDataSource
) : ITravelRepository {

    override suspend fun getTours(
        companyId: String?,
        category: String?,
        minRating: Double?,
        startDate: String?,
        endDate: String?,
        name: String?,
        limit: Int?,
        cursor: String?
    ): Result<PaginatedList<TourItem>> {
        return try {
            val response = remoteDataSource.getTours(
                companyId = companyId,
                category = category,
                minRating = minRating,
                startDate = startDate,
                endDate = endDate,
                name = name,
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

    override suspend fun getTourDetail(id: String): Result<TourDetail> {
        return try {
            val response = remoteDataSource.getTourDetails(id)
            val detail = response.data?.toDomain() ?: throw Exception("Tour not found")
            Result.success(detail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
