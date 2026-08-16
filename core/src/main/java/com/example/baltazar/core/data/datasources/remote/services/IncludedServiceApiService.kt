package com.example.baltazar.core.data.datasources.remote.services

import com.example.baltazar.core.data.model.dto.IncludedServiceDto
import com.example.baltazar.core.data.model.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IncludedServiceApiService {

    @GET("api/services/included-services/{serviceType}")
    suspend fun getIncludedServices(
        @Path("serviceType") serviceType: String
    ): ApiResponse<List<IncludedServiceDto>>
}
