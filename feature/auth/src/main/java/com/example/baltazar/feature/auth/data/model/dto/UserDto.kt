package com.example.baltazar.feature.auth.data.model.dto


import com.example.baltazar.core.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("role") val role: String = "USER",
    @SerialName("avatarUrl") val avatarUrl: String? = null,
    @SerialName("createdAt") val createdAt: String = ""
) {
    fun toDomain(): User {
        return User(
            id = id,
            name = name,
            email = email,
            role = role,
            phone = phone,
            region = region,
            language = language ?: "en",
            avatarUrl = avatarUrl,
            createdAt = createdAt
        )
    }
}
