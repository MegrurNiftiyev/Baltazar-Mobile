package com.example.baltazar.feature.auth.data.datasources.remote.datasources

import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.network.executeRequest
import com.example.baltazar.feature.auth.core.exception.AuthException
import com.example.baltazar.feature.auth.data.model.request.GoogleLoginRequest
import com.example.baltazar.feature.auth.data.model.request.LoginRequest
import com.example.baltazar.feature.auth.data.model.request.RegisterRequest
import com.example.baltazar.feature.auth.data.model.response.AuthResponse
import com.example.baltazar.feature.auth.data.datasources.remote.services.AuthApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
    private val authApiService: AuthApiService
) {
    suspend fun login(email: String, password: String): AuthResponse {
        return executeRequest(
            apiCall = { authApiService.login(LoginRequest(email, password)) },
            expectedErrors = AuthException.allErrors
        )
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String,
        region: Region,
        language: Language
    ): AuthResponse {
        return executeRequest(
            apiCall = {
                authApiService.register(
                    RegisterRequest(
                        name = name,
                        email = email,
                        password = password,
                        phone = phone,
                        region = region.name,
                        language = language.code
                    )
                )
            },
            expectedErrors = AuthException.allErrors
        )
    }

    suspend fun loginWithGoogle(idToken: String): AuthResponse {
        return executeRequest(
            apiCall = { authApiService.loginWithGoogle(GoogleLoginRequest(idToken)) },
            expectedErrors = AuthException.allErrors
        )
    }
}
