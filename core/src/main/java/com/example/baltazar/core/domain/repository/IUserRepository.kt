package com.example.baltazar.core.domain.repository

import com.example.baltazar.core.domain.model.User

interface IUserRepository {
    suspend fun getCurrentUser(): Result<User>

    suspend fun updateProfile(
        name: String? = null,
        phone: String? = null,
        region: String? = null,
        language: String? = null
    ): Result<User>

    suspend fun updatePhoto(avatarUrl: String): Result<User>

    suspend fun updatePersonalInfo(
        dateOfBirth: String? = null,
        address: String? = null,
        idNumber: String? = null
    ): Result<User>

    suspend fun updatePassport(
        passportNumber: String,
        expiryDate: String
    ): Result<User>

    suspend fun updateDriverLicense(
        licenseNumber: String,
        expiryDate: String
    ): Result<User>
}
