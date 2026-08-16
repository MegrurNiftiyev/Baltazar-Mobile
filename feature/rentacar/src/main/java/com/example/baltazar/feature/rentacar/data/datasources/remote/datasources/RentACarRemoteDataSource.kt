package com.example.baltazar.feature.rentacar.data.datasources.remote.datasources

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.rentacar.data.datasources.remote.services.RentACarApiService
import com.example.baltazar.feature.rentacar.data.model.dto.CarDetailDto
import com.example.baltazar.feature.rentacar.data.model.dto.CarDto
import javax.inject.Inject

class RentACarRemoteDataSource @Inject constructor(
    private val apiService: RentACarApiService
) {
    suspend fun getCars(
        companyId: String? = null,
        brand: String? = null,
        model: String? = null,
        category: String? = null,
        transmission: String? = null,
        fuelType: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        limit: Int? = 20,
        cursor: String? = null
    ): PaginatedResponse<CarDto> {
        return executeRequest(
            apiCall = {
                apiService.getCars(
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
    }

    suspend fun getCarDetails(id: String): ApiResponse<CarDetailDto> {
        return executeRequest(
            apiCall = {
                apiService.getCarDetails(id)
            }
        )
    }
}
