package com.example.baltazar.feature.profile.data.repository

import com.example.baltazar.core.domain.model.User
import com.example.baltazar.feature.profile.data.datasources.remote.services.ProfileApiService
import com.example.baltazar.feature.profile.data.model.request.UpdateAvatarRequest
import com.example.baltazar.feature.profile.data.model.request.UpdateProfileRequest
import com.example.baltazar.feature.profile.domain.repository.IProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val apiService: ProfileApiService
) : IProfileRepository {

    override suspend fun updateProfile(request: UpdateProfileRequest): Result<User> {
        return try {
            val response = apiService.updateProfile(request)
            if (response.success && response.data != null) {
                Result.success(response.data.toDomain())
            } else {
                Result.failure(Exception(response.message ?: "Failed to update profile"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAvatar(request: UpdateAvatarRequest): Result<User> {
        return try {
            val response = apiService.updateAvatar(request)
            if (response.success && response.data != null) {
                Result.success(response.data.toDomain())
            } else {
                Result.failure(Exception(response.message ?: "Failed to update avatar"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
