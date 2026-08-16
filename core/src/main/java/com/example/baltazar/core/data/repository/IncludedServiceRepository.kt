package com.example.baltazar.core.data.repository

import com.example.baltazar.core.data.datasources.remote.datasources.IncludedServiceRemoteDataSource
import com.example.baltazar.core.domain.model.IncludedService
import com.example.baltazar.core.domain.repository.IIncludedServiceRepository
import javax.inject.Inject

class IncludedServiceRepository @Inject constructor(
    private val remoteDataSource: IncludedServiceRemoteDataSource
) : IIncludedServiceRepository {

    override suspend fun getIncludedServices(serviceType: String): Result<List<IncludedService>> {
        return try {
            val response = remoteDataSource.getIncludedServices(serviceType)
            val items = response.data?.map { it.toDomain() } ?: emptyList()
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
