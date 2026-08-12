package com.example.baltazar.core.data.repository

import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.data.datasources.remote.datasources.UserRemoteDataSource
import com.example.baltazar.core.data.model.request.toDto
import com.example.baltazar.core.domain.model.DriverLicenseInfo
import com.example.baltazar.core.domain.model.PassportInfo
import com.example.baltazar.core.domain.model.PersonalInfo
import com.example.baltazar.core.domain.model.ProfileImage
import com.example.baltazar.core.domain.model.User
import com.example.baltazar.core.domain.model.UserInfo
import com.example.baltazar.core.domain.repository.IUserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val sessionManager: SessionManager
) : IUserRepository {

    override suspend fun getUser(): Result<User> {
        return try {
            val response = remoteDataSource.getUserProfile()
            val data = response.data
            if (response.success && data != null) {
                val user = data.toDomain()
                sessionManager.set(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to fetch user profile"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(userInfo: UserInfo): Result<User> {
        return try {
            val response = remoteDataSource.updateUserProfile(userInfo.toDto())
            val data = response.data
            if (response.success && data != null) {
                val user = data.toDomain()
                sessionManager.set(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to update user profile"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfilePhoto(profileImage: ProfileImage): Result<User> {
        return try {
            val response = remoteDataSource.updateProfilePhoto(profileImage.toDto())
            val data = response.data
            if (response.success && data != null) {
                val user = data.toDomain()
                sessionManager.set(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to update profile photo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePersonalInfo(personalInfo: PersonalInfo): Result<User> {
        return try {
            val response = remoteDataSource.updatePersonalInfo(personalInfo.toDto())
            val data = response.data
            if (response.success && data != null) {
                val user = data.toDomain()
                sessionManager.set(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to update personal info"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePassportInfo(passportInfo: PassportInfo): Result<User> {
        return try {
            val response = remoteDataSource.updatePassportInfo(passportInfo.toDto())
            val data = response.data
            if (response.success && data != null) {
                val user = data.toDomain()
                sessionManager.set(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to update passport info"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDriverLicense(driverLicenseInfo: DriverLicenseInfo): Result<User> {
        return try {
            val response = remoteDataSource.updateDriverLicense(driverLicenseInfo.toDto())
            val data = response.data
            if (response.success && data != null) {
                val user = data.toDomain()
                sessionManager.set(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to update driver license"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
