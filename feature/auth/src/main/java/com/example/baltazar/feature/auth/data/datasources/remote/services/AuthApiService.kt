package com.example.baltazar.feature.auth.data.datasources.remote.services

import com.example.baltazar.feature.auth.data.model.request.GoogleLoginRequest
import com.example.baltazar.feature.auth.data.model.request.LoginRequest
import com.example.baltazar.feature.auth.data.model.request.RegisterRequest
import com.example.baltazar.feature.auth.data.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("api/auth/google")
    suspend fun loginWithGoogle(@Body request: GoogleLoginRequest): AuthResponse
}
