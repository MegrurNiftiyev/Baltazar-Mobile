package com.example.baltazar.core.data.datasources.remote

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.data.datasources.remote.services.WishlistApiService
import com.example.baltazar.core.data.model.dto.ServiceCardItemDto
import com.example.baltazar.core.data.model.request.AddToWishlistRequest
import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import javax.inject.Inject

class WishlistRemoteDataSource @Inject constructor(
    private val apiService: WishlistApiService
) {
    suspend fun getWishlist(limit: Int, cursor: String?): PaginatedResponse<ServiceCardItemDto> {
        return executeRequest { apiService.getWishlist(limit, cursor) }
    }

    suspend fun addToWishlist(request: AddToWishlistRequest): ApiResponse<Unit> {
        return executeRequest { apiService.addToWishlist(request) }
    }

    suspend fun removeFromWishlist(id: String): ApiResponse<Unit> {
        return executeRequest { apiService.removeFromWishlist(id) }
    }
}
