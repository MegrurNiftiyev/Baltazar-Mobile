package com.example.baltazar.core.data.datasources.remote

import com.example.baltazar.core.data.datasources.remote.services.RefreshTokenApiService
import com.example.baltazar.core.data.model.request.RefreshTokenRequest
import com.example.baltazar.core.data.model.response.RefreshTokenResponse
import javax.inject.Inject

class RefreshTokenDataSource @Inject constructor(
    private val apiService: RefreshTokenApiService
) {
    suspend fun refresh(request: RefreshTokenRequest): RefreshTokenResponse {
        return apiService.refresh(request)
    }
}
