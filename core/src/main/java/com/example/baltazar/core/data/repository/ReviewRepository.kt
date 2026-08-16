package com.example.baltazar.core.data.repository

import com.example.baltazar.core.data.datasources.remote.datasources.ReviewRemoteDataSource
import com.example.baltazar.core.data.model.request.CreateReviewRequest
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.PaginationInfo
import com.example.baltazar.core.domain.model.ReviewItem
import com.example.baltazar.core.domain.repository.IReviewRepository
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val remoteDataSource: ReviewRemoteDataSource
) : IReviewRepository {

    override suspend fun getReviews(
        targetType: String,
        targetId: String,
        limit: Int?,
        cursor: String?
    ): Result<PaginatedList<ReviewItem>> {
        return try {
            val response = remoteDataSource.getReviews(
                targetType = targetType,
                targetId = targetId,
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

    override suspend fun createReview(
        targetType: String,
        targetId: String,
        rating: Int,
        comment: String
    ): Result<ReviewItem?> {
        return try {
            val response = remoteDataSource.createReview(
                CreateReviewRequest(
                    targetType = targetType,
                    targetId = targetId,
                    rating = rating,
                    comment = comment
                )
            )
            Result.success(response.data?.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
