package com.example.baltazar.core.domain.repository

import com.example.baltazar.core.domain.model.ServiceCardItem
import com.example.baltazar.core.core.enums.ServiceType

interface IWishlistRepository {
    suspend fun getWishlist(limit: Int = 20, cursor: String? = null): Result<List<ServiceCardItem>>
    suspend fun addToWishlist(serviceId: String, serviceType: ServiceType): Result<Unit>
    suspend fun removeFromWishlist(id: String): Result<Unit>
}
