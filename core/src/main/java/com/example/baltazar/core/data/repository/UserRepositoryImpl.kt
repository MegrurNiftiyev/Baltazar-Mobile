package com.example.baltazar.core.data.repository

import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.data.datasources.remote.UserRemoteDataSource
import com.example.baltazar.core.data.model.request.UpdateDriverLicenseRequestDto
import com.example.baltazar.core.data.model.request.UpdatePassportRequestDto
import com.example.baltazar.core.data.model.request.UpdatePersonalInfoRequestDto
import com.example.baltazar.core.data.model.request.UpdatePhotoRequestDto
import com.example.baltazar.core.data.model.request.UpdateProfileRequestDto
import com.example.baltazar.core.domain.model.User
import com.example.baltazar.core.domain.repository.IUserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val sessionManager: SessionManager
) : IUserRepository {

    override suspend fun getCurrentUser(): Result<User> = runCatching {
        val response = remoteDataSource.getCurrentUser()
        val userDto = response.data ?: throw Exception("Empty user response body")
        val user = userDto.toDomain()
        sessionManager.set(user)
        user
    }

    override suspend fun updateProfile(
        name: String?,
        phone: String?,
        region: String?,
        language: String?
    ): Result<User> = runCatching {
        val request = UpdateProfileRequestDto(
            name = name,
            phone = phone,
            region = region,
            language = language
        )
        val response = remoteDataSource.updateProfile(request)
        val userDto = response.data ?: throw Exception("Empty user response body")
        val user = userDto.toDomain()
        sessionManager.set(user)
        user
    }

    override suspend fun updatePhoto(avatarUrl: String): Result<User> = runCatching {
        val request = UpdatePhotoRequestDto(avatarUrl = avatarUrl)
        val response = remoteDataSource.updatePhoto(request)
        val userDto = response.data ?: throw Exception("Empty user response body")
        val user = userDto.toDomain()
        sessionManager.set(user)
        user
    }

    override suspend fun updatePersonalInfo(
        dateOfBirth: String?,
        address: String?,
        idNumber: String?
    ): Result<User> = runCatching {
        val request = UpdatePersonalInfoRequestDto(
            dateOfBirth = dateOfBirth,
            address = address,
            idNumber = idNumber
        )
        val response = remoteDataSource.updatePersonalInfo(request)
        val userDto = response.data ?: throw Exception("Empty user response body")
        val user = userDto.toDomain()
        sessionManager.set(user)
        user
    }

    override suspend fun updatePassport(
        passportNumber: String,
        expiryDate: String
    ): Result<User> = runCatching {
        val request = UpdatePassportRequestDto(
            passportNumber = passportNumber,
            expiryDate = expiryDate
        )
        val response = remoteDataSource.updatePassport(request)
        val userDto = response.data ?: throw Exception("Empty user response body")
        val user = userDto.toDomain()
        sessionManager.set(user)
        user
    }

    override suspend fun updateDriverLicense(
        licenseNumber: String,
        expiryDate: String
    ): Result<User> = runCatching {
        val request = UpdateDriverLicenseRequestDto(
            licenseNumber = licenseNumber,
            expiryDate = expiryDate
        )
        val response = remoteDataSource.updateDriverLicense(request)
        val userDto = response.data ?: throw Exception("Empty user response body")
        val user = userDto.toDomain()
        sessionManager.set(user)
        user
    }
}
