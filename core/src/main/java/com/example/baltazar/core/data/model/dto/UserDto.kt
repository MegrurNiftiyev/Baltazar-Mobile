package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileCompletenessDto(
    @SerialName("personalInfo") val personalInfo: Boolean? = false,
    @SerialName("driverLicense") val driverLicense: Boolean? = false,
    @SerialName("passport") val passport: Boolean? = false
)

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("role") val role: String,
    @SerialName("phone") val phone: String? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("language") val language: String = "en",
    @SerialName("avatarUrl") val avatarUrl: String? = null,
    @SerialName("personalInfo") val personalInfo: Boolean? = false,
    @SerialName("driverLicense") val driverLicense: Boolean? = false,
    @SerialName("passport") val passport: Boolean? = false,
    @SerialName("profileCompleteness") val profileCompleteness: ProfileCompletenessDto? = null,
    @SerialName("createdAt") val createdAt: String
) {
    fun toDomain(): User {
        return User(
            id = id,
            name = name,
            email = email,
            role = role,
            phone = phone,
            region = region,
            language = language,
            avatarUrl = avatarUrl,
            personalInfo = personalInfo ?: profileCompleteness?.personalInfo ?: false,
            driverLicense = driverLicense ?: profileCompleteness?.driverLicense ?: false,
            passport = passport ?: profileCompleteness?.passport ?: false,
            createdAt = createdAt
        )
    }
}
