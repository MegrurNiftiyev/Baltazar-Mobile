package com.example.baltazar.core.data.datasources.remote

import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.core.data.datasources.remote.services.UserApiService
import com.example.baltazar.core.data.model.request.UpdateDriverLicenseRequestDto
import com.example.baltazar.core.data.model.request.UpdatePassportRequestDto
import com.example.baltazar.core.data.model.request.UpdatePersonalInfoRequestDto
import com.example.baltazar.core.data.model.request.UpdatePhotoRequestDto
import com.example.baltazar.core.data.model.request.UpdateProfileRequestDto
import com.example.baltazar.core.data.model.response.UserResponse
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: UserApiService
) {
    suspend fun getCurrentUser(): UserResponse {
        return executeRequest { apiService.getCurrentUser() }
    }

    suspend fun updateProfile(request: UpdateProfileRequestDto): UserResponse {
        return executeRequest { apiService.updateProfile(request) }
    }

    suspend fun updatePhoto(request: UpdatePhotoRequestDto): UserResponse {
        return executeRequest { apiService.updatePhoto(request) }
    }

    suspend fun updatePersonalInfo(request: UpdatePersonalInfoRequestDto): UserResponse {
        return executeRequest { apiService.updatePersonalInfo(request) }
    }

    suspend fun updatePassport(request: UpdatePassportRequestDto): UserResponse {
        return executeRequest { apiService.updatePassport(request) }
    }

    suspend fun updateDriverLicense(request: UpdateDriverLicenseRequestDto): UserResponse {
        return executeRequest { apiService.updateDriverLicense(request) }
    }
}
