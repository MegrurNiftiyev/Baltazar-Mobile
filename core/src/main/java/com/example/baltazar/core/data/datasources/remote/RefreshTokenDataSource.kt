package com.example.baltazar.core.data.datasources.remote

import com.example.baltazar.core.data.model.request.RefreshTokenRequest
import com.example.baltazar.core.data.model.response.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenDataSource {
    @POST("api/auth/refresh-token")
    suspend fun refresh(@Body request: RefreshTokenRequest): RefreshTokenResponse
}
