package com.example.baltazar.feature.auth.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDataDto(
    @SerialName("user") val user: UserDto,
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String
)
