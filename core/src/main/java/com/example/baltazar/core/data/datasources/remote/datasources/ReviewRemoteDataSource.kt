package com.example.baltazar.core.data.datasources.remote.datasources

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.data.datasources.remote.services.ReviewApiService
import com.example.baltazar.core.data.model.dto.ReviewDto
import com.example.baltazar.core.data.model.request.CreateReviewRequest
import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import javax.inject.Inject

class ReviewRemoteDataSource @Inject constructor(
    private val apiService: ReviewApiService
) {
    suspend fun getReviews(
        targetType: String,
        targetId: String,
        limit: Int? = 20,
        cursor: String? = null
    ): PaginatedResponse<ReviewDto> {
        return executeRequest(
            apiCall = {
                apiService.getReviews(
                    targetType = targetType,
                    targetId = targetId,
                    limit = limit,
                    cursor = cursor
                )
            }
        )
    }

    suspend fun createReview(request: CreateReviewRequest): ApiResponse<ReviewDto> {
        return executeRequest(
            apiCall = {
                apiService.createReview(request)
            }
        )
    }
}
