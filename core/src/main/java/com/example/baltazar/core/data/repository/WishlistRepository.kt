package com.example.baltazar.core.data.repository

import com.example.baltazar.core.data.datasources.remote.WishlistRemoteDataSource
import com.example.baltazar.core.data.model.request.AddToWishlistRequest
import com.example.baltazar.core.domain.model.ServiceCardItem
import com.example.baltazar.core.domain.repository.IWishlistRepository
import com.example.baltazar.core.core.enums.ServiceType
import javax.inject.Inject

class WishlistRepository @Inject constructor(
    private val remoteDataSource: WishlistRemoteDataSource
) : IWishlistRepository {

    override suspend fun getWishlist(limit: Int, cursor: String?): Result<List<ServiceCardItem>> {
        return try {
            val response = remoteDataSource.getWishlist(limit, cursor)
            Result.success(response.data.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addToWishlist(serviceId: String, serviceType: ServiceType): Result<Unit> {
        return try {
            remoteDataSource.addToWishlist(
                AddToWishlistRequest(
                    serviceId = serviceId,
                    serviceType = serviceType.name
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromWishlist(id: String): Result<Unit> {
        return try {
            remoteDataSource.removeFromWishlist(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
