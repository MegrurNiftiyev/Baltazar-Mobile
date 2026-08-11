package com.example.baltazar.feature.auth.data.model.response

import com.example.baltazar.feature.auth.data.model.dto.AuthDataDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: AuthDataDto? = null
)
