package com.example.baltazar.feature.auth.data.repository

import com.example.baltazar.feature.auth.domain.repository.IAuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() : IAuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String
    ): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun loginWithGoogle(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun logout(): Result<Unit> {
        return Result.success(Unit)
    }
}
