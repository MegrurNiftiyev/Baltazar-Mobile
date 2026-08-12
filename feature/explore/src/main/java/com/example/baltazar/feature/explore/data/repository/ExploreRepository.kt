package com.example.baltazar.feature.explore.data.repository

import com.example.baltazar.feature.explore.data.datasources.remote.datasources.ExploreRemoteDataSource
import com.example.baltazar.feature.explore.domain.model.BannerItem
import com.example.baltazar.feature.explore.domain.model.ExploreSection
import com.example.baltazar.feature.explore.domain.repository.IExploreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreRepository @Inject constructor(
    private val remoteDataSource: ExploreRemoteDataSource
) : IExploreRepository {

    override suspend fun getBanners(): Result<List<BannerItem>> {
        return try {
            val response = remoteDataSource.getBanners()
            val data = response.data
            if (response.success && data != null) {
                Result.success(data.map { it.toDomain() })
            } else {
                Result.failure(Exception("Failed to fetch banners"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExploreSections(): Result<List<ExploreSection>> {
        return try {
            val response = remoteDataSource.getExploreSections()
            val data = response.data
            if (response.success && data != null) {
                val sections = data.map { it.toDomain() }.sortedBy { it.order }
                Result.success(sections)
            } else {
                Result.failure(Exception("Failed to fetch explore sections"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
