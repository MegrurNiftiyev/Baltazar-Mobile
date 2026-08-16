package com.example.baltazar.core.data.model.response

import com.example.baltazar.core.data.model.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: UserDto? = null
)

typealias UserResponse = UserResponseDto
