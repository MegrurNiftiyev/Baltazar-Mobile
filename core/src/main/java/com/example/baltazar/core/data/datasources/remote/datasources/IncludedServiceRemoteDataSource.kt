package com.example.baltazar.core.data.datasources.remote.datasources

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.data.datasources.remote.services.IncludedServiceApiService
import com.example.baltazar.core.data.model.dto.IncludedServiceDto
import com.example.baltazar.core.data.model.response.ApiResponse
import javax.inject.Inject

class IncludedServiceRemoteDataSource @Inject constructor(
    private val apiService: IncludedServiceApiService
) {
    suspend fun getIncludedServices(serviceType: String): ApiResponse<List<IncludedServiceDto>> {
        return executeRequest(
            apiCall = {
                apiService.getIncludedServices(serviceType)
            }
        )
    }
}
