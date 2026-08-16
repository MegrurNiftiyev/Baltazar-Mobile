package com.example.baltazar.core.data.datasources.remote.services

import com.example.baltazar.core.data.model.dto.ReviewDto
import com.example.baltazar.core.data.model.request.CreateReviewRequest
import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReviewApiService {

    @GET("api/reviews")
    suspend fun getReviews(
        @Query("targetType") targetType: String,
        @Query("targetId") targetId: String,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): PaginatedResponse<ReviewDto>

    @POST("api/reviews")
    suspend fun createReview(
        @Body request: CreateReviewRequest
    ): ApiResponse<ReviewDto>
}
