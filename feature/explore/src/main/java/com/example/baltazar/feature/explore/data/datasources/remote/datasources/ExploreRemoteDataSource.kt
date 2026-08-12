package com.example.baltazar.feature.explore.data.datasources.remote.datasources

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.feature.explore.core.exception.ExploreException
import com.example.baltazar.feature.explore.data.datasources.remote.services.ExploreApiService
import com.example.baltazar.feature.explore.data.model.response.BannerResponse
import com.example.baltazar.feature.explore.data.model.response.ExploreResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreRemoteDataSource @Inject constructor(
    private val exploreApiService: ExploreApiService
) {
    suspend fun getBanners(): BannerResponse {
        return executeRequest(
            apiCall = { exploreApiService.getBanners() },
            expectedErrors = ExploreException.allErrors
        )
    }

    suspend fun getExploreSections(): ExploreResponse {
        return executeRequest(
            apiCall = { exploreApiService.getExploreSections() },
            expectedErrors = ExploreException.allErrors
        )
    }
}
