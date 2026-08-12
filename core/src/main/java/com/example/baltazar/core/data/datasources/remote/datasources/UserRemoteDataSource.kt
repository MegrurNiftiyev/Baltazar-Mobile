package com.example.baltazar.core.data.datasources.remote.datasources

import com.example.baltazar.core.data.datasources.remote.services.UserApiService
import com.example.baltazar.core.data.model.request.UpdateDriverLicenseDto
import com.example.baltazar.core.data.model.request.UpdatePassportInfoDto
import com.example.baltazar.core.data.model.request.UpdatePersonalInfoDto
import com.example.baltazar.core.data.model.request.UpdateProfilePhotoDto
import com.example.baltazar.core.data.model.request.UpdateUserDto
import com.example.baltazar.core.data.model.response.UserResponse
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: UserApiService
) {
    suspend fun getUserProfile(): UserResponse {
        return apiService.getUserProfile()
    }

    suspend fun updateUserProfile(request: UpdateUserDto): UserResponse {
        return apiService.updateUserProfile(request)
    }

    suspend fun updateProfilePhoto(request: UpdateProfilePhotoDto): UserResponse {
        return apiService.updateProfilePhoto(request)
    }

    suspend fun updatePersonalInfo(request: UpdatePersonalInfoDto): UserResponse {
        return apiService.updatePersonalInfo(request)
    }

    suspend fun updatePassportInfo(request: UpdatePassportInfoDto): UserResponse {
        return apiService.updatePassportInfo(request)
    }

    suspend fun updateDriverLicense(request: UpdateDriverLicenseDto): UserResponse {
        return apiService.updateDriverLicense(request)
    }
}
