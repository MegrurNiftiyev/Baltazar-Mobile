package com.example.baltazar.core.data.model.response

import com.example.baltazar.core.data.model.dto.RefreshTokenDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: RefreshTokenDto? = null
)
