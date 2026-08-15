package com.example.baltazar.feature.rentacar.data.repository

import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.PaginationInfo
import com.example.baltazar.feature.rentacar.data.datasources.remote.datasources.RentACarRemoteDataSource
import com.example.baltazar.feature.rentacar.domain.model.CarDetail
import com.example.baltazar.feature.rentacar.domain.model.CarItem
import com.example.baltazar.feature.rentacar.domain.repository.IRentACarRepository
import javax.inject.Inject

class RentACarRepository @Inject constructor(
    private val remoteDataSource: RentACarRemoteDataSource
) : IRentACarRepository {

    override suspend fun getCars(
        companyId: String?,
        brand: String?,
        model: String?,
        category: String?,
        transmission: String?,
        fuelType: String?,
        minPrice: Double?,
        maxPrice: Double?,
        limit: Int?,
        cursor: String?
    ): Result<PaginatedList<CarItem>> {
        return try {
            val response = remoteDataSource.getCars(
                companyId = companyId,
                brand = brand,
                model = model,
                category = category,
                transmission = transmission,
                fuelType = fuelType,
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

    override suspend fun getCarDetail(id: String): Result<CarDetail> {
        return try {
            val response = remoteDataSource.getCarDetails(id)
            val detail = response.data?.toDomain() ?: throw Exception("Car not found")
            Result.success(detail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
