package com.example.baltazar.core.domain.repository

import com.example.baltazar.core.domain.model.IncludedService

interface IIncludedServiceRepository {
    suspend fun getIncludedServices(serviceType: String): Result<List<IncludedService>>
}
