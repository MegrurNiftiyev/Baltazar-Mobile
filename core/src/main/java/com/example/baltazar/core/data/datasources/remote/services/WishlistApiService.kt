package com.example.baltazar.core.data.datasources.remote.services

import com.example.baltazar.core.data.model.dto.ServiceCardItemDto
import com.example.baltazar.core.data.model.request.AddToWishlistRequest
import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WishlistApiService {
    @GET("api/user/wishlist")
    suspend fun getWishlist(
        @Query("limit") limit: Int = 20,
        @Query("cursor") cursor: String? = null
    ): PaginatedResponse<ServiceCardItemDto>

    @POST("api/user/wishlist")
    suspend fun addToWishlist(
        @Body request: AddToWishlistRequest
    ): ApiResponse<Unit>

    @DELETE("api/user/wishlist/{id}")
    suspend fun removeFromWishlist(
        @Path("id") id: String
    ): ApiResponse<Unit>
}
