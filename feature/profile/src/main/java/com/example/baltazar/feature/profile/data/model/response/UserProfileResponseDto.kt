package com.example.baltazar.feature.profile.data.model.response

import com.example.baltazar.core.data.model.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: UserDto? = null,
    @SerialName("message") val message: String? = null
)
