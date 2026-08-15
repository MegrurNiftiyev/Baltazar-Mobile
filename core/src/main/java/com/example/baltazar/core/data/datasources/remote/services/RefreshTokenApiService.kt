package com.example.baltazar.core.data.datasources.remote.services

import com.example.baltazar.core.data.model.request.RefreshTokenRequest
import com.example.baltazar.core.data.model.response.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenApiService {
    @POST("api/auth/refresh-token")
    suspend fun refresh(@Body request: RefreshTokenRequest): RefreshTokenResponse
}
