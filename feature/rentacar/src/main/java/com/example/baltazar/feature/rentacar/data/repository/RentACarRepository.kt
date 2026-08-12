package com.example.baltazar.feature.rentacar.data.repository

import com.example.baltazar.core.core.exceptions.NetworkException
import com.example.baltazar.feature.rentacar.data.source.remote.RentACarApi
import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.rentacar.domain.model.CarItem
import com.example.baltazar.feature.rentacar.domain.repository.IRentACarRepository
import javax.inject.Inject

class RentACarRepository @Inject constructor(
    private val api: RentACarApi
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
            val response = executeRequest(
                apiCall = {
                    api.getCars(
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
                }
            )
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
