package com.example.baltazar.feature.auth.domain.repository

import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.domain.model.User

interface IAuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String,
        region: Region,
        language: Language
    ): Result<User>
    suspend fun loginWithGoogle(idToken: String): Result<User>
}
