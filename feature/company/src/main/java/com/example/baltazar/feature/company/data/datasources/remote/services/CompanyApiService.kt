package com.example.baltazar.feature.company.data.datasources.remote.services

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.company.data.model.dto.CompanyDetailsDto
import com.example.baltazar.feature.company.data.model.dto.CompanyListItemDto
import com.example.baltazar.feature.company.data.model.dto.RelatedItemDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyApiService {

    @GET("api/companies")
    suspend fun getCompanies(
        @Query("serviceType") serviceType: String? = null,
        @Query("limit") limit: Int? = 20,
        @Query("cursor") cursor: String? = null
    ): PaginatedResponse<CompanyListItemDto>

    @GET("api/companies/{id}")
    suspend fun getCompanyDetails(
        @Path("id") id: String
    ): ApiResponse<CompanyDetailsDto>

    @GET("api/companies/{id}/items")
    suspend fun getRelatedItems(
        @Path("id") id: String
    ): ApiResponse<List<RelatedItemDto>>
}
