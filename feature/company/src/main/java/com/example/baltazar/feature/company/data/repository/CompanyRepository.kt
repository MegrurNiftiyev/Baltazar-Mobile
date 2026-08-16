package com.example.baltazar.feature.company.data.repository

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.PaginationInfo
import com.example.baltazar.feature.company.core.exception.CompanyException
import com.example.baltazar.feature.company.data.datasources.remote.CompanyRemoteDataSource
import com.example.baltazar.feature.company.domain.model.Company
import com.example.baltazar.feature.company.domain.model.CompanyDetail
import com.example.baltazar.feature.company.domain.model.RelatedItem
import com.example.baltazar.feature.company.domain.repository.ICompanyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyRepository @Inject constructor(
    private val remoteDataSource: CompanyRemoteDataSource
) : ICompanyRepository {

    override suspend fun getCompanies(
        serviceType: ServiceType?,
        cursor: String?,
        limit: Int
    ): Result<PaginatedList<Company>> {
        return try {
            val response = remoteDataSource.getCompanies(serviceType?.name, limit, cursor)
            if (response.success) {
                val companies = response.data.map { it.toDomain() }
                val pagination = response.pagination?.toDomain()
                    ?: PaginationInfo(nextCursor = null, hasMore = false, limit = limit)
                Result.success(PaginatedList(items = companies, pagination = pagination))
            } else {
                Result.failure(CompanyException.FetchFailed)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCompanyDetails(companyId: String): Result<CompanyDetail> {
        return try {
            val response = remoteDataSource.getCompanyDetails(companyId)
            val data = response.data
            if (response.success && data != null) {
                Result.success(data.toDomain())
            } else {
                Result.failure(CompanyException.CompanyNotFound)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRelatedItems(companyId: String): Result<List<RelatedItem>> {
        return try {
            val response = remoteDataSource.getRelatedItems(companyId)
            val data = response.data
            if (response.success && data != null) {
                Result.success(data.map { it.toDomain() })
            } else {
                Result.success(emptyList())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
