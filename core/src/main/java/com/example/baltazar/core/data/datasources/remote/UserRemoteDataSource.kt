package com.example.baltazar.core.data.datasources.remote

import com.example.baltazar.core.data.datasources.remote.services.UserApiService
import com.example.baltazar.core.data.model.request.UpdateDriverLicenseRequestDto
import com.example.baltazar.core.data.model.request.UpdatePassportRequestDto
import com.example.baltazar.core.data.model.request.UpdatePersonalInfoRequestDto
import com.example.baltazar.core.data.model.request.UpdatePhotoRequestDto
import com.example.baltazar.core.data.model.request.UpdateProfileRequestDto
import com.example.baltazar.core.data.model.response.UserResponseDto
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: UserApiService
) {
    suspend fun getCurrentUser(): UserResponseDto {
        return apiService.getCurrentUser()
    }

    suspend fun updateProfile(request: UpdateProfileRequestDto): UserResponseDto {
        return apiService.updateProfile(request)
    }

    suspend fun updatePhoto(request: UpdatePhotoRequestDto): UserResponseDto {
        return apiService.updatePhoto(request)
    }

    suspend fun updatePersonalInfo(request: UpdatePersonalInfoRequestDto): UserResponseDto {
        return apiService.updatePersonalInfo(request)
    }

    suspend fun updatePassport(request: UpdatePassportRequestDto): UserResponseDto {
        return apiService.updatePassport(request)
    }

    suspend fun updateDriverLicense(request: UpdateDriverLicenseRequestDto): UserResponseDto {
        return apiService.updateDriverLicense(request)
    }
}
