package com.example.baltazar.core.data.datasources.remote.services

import com.example.baltazar.core.data.model.request.UpdateDriverLicenseRequestDto
import com.example.baltazar.core.data.model.request.UpdatePassportRequestDto
import com.example.baltazar.core.data.model.request.UpdatePersonalInfoRequestDto
import com.example.baltazar.core.data.model.request.UpdatePhotoRequestDto
import com.example.baltazar.core.data.model.request.UpdateProfileRequestDto
import com.example.baltazar.core.data.model.response.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserApiService {
    @GET("api/users/me")
    suspend fun getCurrentUser(): UserResponseDto

    @PATCH("api/users/me")
    suspend fun updateProfile(@Body body: UpdateProfileRequestDto): UserResponseDto

    @PATCH("api/users/me/photo")
    suspend fun updatePhoto(@Body body: UpdatePhotoRequestDto): UserResponseDto

    @PATCH("api/users/me/personal-info")
    suspend fun updatePersonalInfo(@Body body: UpdatePersonalInfoRequestDto): UserResponseDto

    @PATCH("api/users/me/passport")
    suspend fun updatePassport(@Body body: UpdatePassportRequestDto): UserResponseDto

    @PATCH("api/users/me/driver-license")
    suspend fun updateDriverLicense(@Body body: UpdateDriverLicenseRequestDto): UserResponseDto
}
