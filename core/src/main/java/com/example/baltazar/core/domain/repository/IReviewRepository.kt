package com.example.baltazar.core.domain.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.ReviewItem

interface IReviewRepository {
    suspend fun getReviews(
        targetType: String,
        targetId: String,
        limit: Int? = 20,
        cursor: String? = null
    ): Result<PaginatedList<ReviewItem>>

    suspend fun createReview(
        targetType: String,
        targetId: String,
        rating: Int,
        comment: String
    ): Result<ReviewItem?>
}
