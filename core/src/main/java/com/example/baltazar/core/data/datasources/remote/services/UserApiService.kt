package com.example.baltazar.core.data.datasources.remote.services

import com.example.baltazar.core.data.model.request.UpdateDriverLicenseDto
import com.example.baltazar.core.data.model.request.UpdatePassportInfoDto
import com.example.baltazar.core.data.model.request.UpdatePersonalInfoDto
import com.example.baltazar.core.data.model.request.UpdateProfilePhotoDto
import com.example.baltazar.core.data.model.request.UpdateUserDto
import com.example.baltazar.core.data.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApiService {
    @GET("user/profile")
    suspend fun getUserProfile(): UserResponse

    @PUT("user/profile")
    suspend fun updateUserProfile(@Body request: UpdateUserDto): UserResponse

    @PUT("user/profile/photo")
    suspend fun updateProfilePhoto(@Body request: UpdateProfilePhotoDto): UserResponse

    @PUT("user/profile/personal-info")
    suspend fun updatePersonalInfo(@Body request: UpdatePersonalInfoDto): UserResponse

    @PUT("user/profile/passport")
    suspend fun updatePassportInfo(@Body request: UpdatePassportInfoDto): UserResponse

    @PUT("user/profile/driver-license")
    suspend fun updateDriverLicense(@Body request: UpdateDriverLicenseDto): UserResponse
}
