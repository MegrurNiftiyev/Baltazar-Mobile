package com.example.baltazar.feature.company.domain.repository

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.company.domain.model.Company
import com.example.baltazar.feature.company.domain.model.CompanyDetail
import com.example.baltazar.feature.company.domain.model.RelatedItem

interface ICompanyRepository {
    suspend fun getCompanies(
        serviceType: ServiceType? = null,
        cursor: String? = null,
        limit: Int = 20
    ): Result<PaginatedList<Company>>

    suspend fun getCompanyDetails(companyId: String): Result<CompanyDetail>

    suspend fun getRelatedItems(companyId: String): Result<List<RelatedItem>>
}
