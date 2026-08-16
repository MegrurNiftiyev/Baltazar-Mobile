package com.example.baltazar.feature.profile.domain.repository

import com.example.baltazar.core.domain.model.User
import com.example.baltazar.feature.profile.data.model.request.UpdateAvatarRequest
import com.example.baltazar.feature.profile.data.model.request.UpdateProfileRequest

interface IProfileRepository {
    suspend fun updateProfile(request: UpdateProfileRequest): Result<User>
    suspend fun updateAvatar(request: UpdateAvatarRequest): Result<User>
}
