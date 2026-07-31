package com.example.baltazar.feature.auth.domain.repository

interface IAuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(name: String, email: String, password: String, phone: String): Result<Unit>
    suspend fun loginWithGoogle(): Result<Unit>
    suspend fun logout(): Result<Unit>
}
