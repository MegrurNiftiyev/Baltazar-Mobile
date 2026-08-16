package com.example.baltazar.feature.profile.data.datasources.remote.services

import com.example.baltazar.feature.profile.data.model.request.UpdateAvatarRequest
import com.example.baltazar.feature.profile.data.model.request.UpdateProfileRequest
import com.example.baltazar.feature.profile.data.model.response.UserProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.PUT

interface ProfileApiService {
    @PUT("api/users/me")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): UserProfileResponseDto

    @PUT("api/users/me/avatar")
    suspend fun updateAvatar(
        @Body request: UpdateAvatarRequest
    ): UserProfileResponseDto
}
