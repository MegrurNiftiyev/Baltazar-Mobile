package com.example.baltazar.feature.auth.data.repository

import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.managers.CacheManager
import com.example.baltazar.core.core.managers.EncryptedCacheManager
import com.example.baltazar.feature.auth.core.exception.AuthException
import com.example.baltazar.feature.auth.data.datasources.remote.datasources.AuthRemoteDataSource
import com.example.baltazar.feature.auth.domain.model.User
import com.example.baltazar.feature.auth.domain.repository.IAuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val encryptedCacheManager: EncryptedCacheManager,
    private val cacheManager: CacheManager
) : IAuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = authRemoteDataSource.login(email, password)
            val authData = response.data

            if (response.success && authData != null) {
                saveTokensAndState(authData.accessToken, authData.refreshToken)
                Result.success(authData.user.toDomain())
            } else {
                Result.failure(AuthException.InvalidCredentials)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String,
        region: Region,
        language: Language
    ): Result<User> {
        return try {
            val response = authRemoteDataSource.register(name, email, password, phone, region, language)
            val authData = response.data

            if (response.success && authData != null) {
                saveTokensAndState(authData.accessToken, authData.refreshToken)
                Result.success(authData.user.toDomain())
            } else {
                Result.failure(AuthException.ValidationError)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> {
        return try {
            val response = authRemoteDataSource.loginWithGoogle(idToken)
            val authData = response.data

            if (response.success && authData != null) {
                saveTokensAndState(authData.accessToken, authData.refreshToken)
                Result.success(authData.user.toDomain())
            } else {
                Result.failure(AuthException.InvalidCredentials)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun saveTokensAndState(accessToken: String, refreshToken: String) {
        encryptedCacheManager.saveSecureString(CacheKeys.ACCESS_TOKEN, accessToken)
        encryptedCacheManager.saveSecureString(CacheKeys.REFRESH_TOKEN, refreshToken)
        cacheManager.setBoolean(CacheKeys.IS_LOGIN_FINISHED, true)
    }
}
