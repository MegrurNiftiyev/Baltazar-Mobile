package com.example.baltazar.feature.food.data.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.PaginationInfo
import com.example.baltazar.feature.food.data.datasources.remote.datasources.FoodRemoteDataSource
import com.example.baltazar.feature.food.domain.model.FoodDetail
import com.example.baltazar.feature.food.domain.model.FoodItem
import com.example.baltazar.feature.food.domain.repository.IFoodRepository
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val remoteDataSource: FoodRemoteDataSource
) : IFoodRepository {

    override suspend fun getFoodItems(
        companyId: String?,
        category: String?,
        name: String?,
        minPrice: Double?,
        maxPrice: Double?,
        limit: Int?,
        cursor: String?
    ): Result<PaginatedList<FoodItem>> {
        return try {
            val response = remoteDataSource.getFoodItems(
                companyId = companyId,
                category = category,
                name = name,
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

    override suspend fun getFoodDetail(id: String): Result<FoodDetail> {
        return try {
            val response = remoteDataSource.getFoodItemDetails(id)
            val detail = response.data?.toDomain() ?: throw Exception("Food item not found")
            Result.success(detail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
