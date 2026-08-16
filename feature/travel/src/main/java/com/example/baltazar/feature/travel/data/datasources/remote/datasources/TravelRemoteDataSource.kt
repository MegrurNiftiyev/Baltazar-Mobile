package com.example.baltazar.feature.travel.data.datasources.remote.datasources

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.travel.data.datasources.remote.services.TravelApiService
import com.example.baltazar.feature.travel.data.model.dto.TourDetailDto
import com.example.baltazar.feature.travel.data.model.dto.TourDto
import javax.inject.Inject

class TravelRemoteDataSource @Inject constructor(
    private val apiService: TravelApiService
) {
    suspend fun getTours(
        companyId: String? = null,
        category: String? = null,
        minRating: Double? = null,
        startDate: String? = null,
        endDate: String? = null,
        name: String? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): PaginatedResponse<TourDto> {
        return executeRequest(
            apiCall = {
                apiService.getTours(
                    companyId = companyId,
                    category = category,
                    minRating = minRating,
                    startDate = startDate,
                    endDate = endDate,
                    name = name,
                    limit = limit,
                    cursor = cursor
                )
            }
        )
    }

    suspend fun getTourDetails(id: String): ApiResponse<TourDetailDto> {
        return executeRequest(
            apiCall = {
                apiService.getTourDetails(id)
            }
        )
    }
}
