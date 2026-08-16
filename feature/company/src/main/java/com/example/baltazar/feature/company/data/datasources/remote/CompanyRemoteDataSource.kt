package com.example.baltazar.feature.company.data.datasources.remote

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.company.data.datasources.remote.services.CompanyApiService
import com.example.baltazar.feature.company.data.model.dto.CompanyDetailsDto
import com.example.baltazar.feature.company.data.model.dto.CompanyListItemDto
import com.example.baltazar.feature.company.data.model.dto.RelatedItemDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyRemoteDataSource @Inject constructor(
    private val apiService: CompanyApiService
) {
    suspend fun getCompanies(
        serviceType: String?,
        limit: Int?,
        cursor: String?
    ): PaginatedResponse<CompanyListItemDto> {
        return apiService.getCompanies(
            serviceType = serviceType,
            limit = limit,
            cursor = cursor
        )
    }

    suspend fun getCompanyDetails(id: String): ApiResponse<CompanyDetailsDto> {
        return apiService.getCompanyDetails(id)
    }

    suspend fun getRelatedItems(id: String): ApiResponse<List<RelatedItemDto>> {
        return apiService.getRelatedItems(id)
    }
}
