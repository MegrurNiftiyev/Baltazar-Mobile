package com.example.baltazar.feature.travel.data.repository

import com.example.baltazar.core.core.exceptions.NetworkException
import com.example.baltazar.feature.travel.data.source.remote.TravelApi
import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.travel.domain.model.TourItem
import com.example.baltazar.feature.travel.domain.repository.ITravelRepository
import javax.inject.Inject

class TravelRepository @Inject constructor(
    private val api: TravelApi
) : ITravelRepository {

    override suspend fun getTours(
        companyId: String?,
        category: String?,
        title: String?,
        minPrice: Double?,
        maxPrice: Double?,
        minRating: Double?,
        startDate: String?,
        endDate: String?,
        limit: Int?,
        cursor: String?
    ): Result<PaginatedList<TourItem>> {
        return try {
            val response = executeRequest(
                apiCall = {
                    api.getTours(
                        companyId = companyId,
                        category = category,
                        title = title,
                        minPrice = minPrice,
                        maxPrice = maxPrice,
                        minRating = minRating,
                        startDate = startDate,
                        endDate = endDate,
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
