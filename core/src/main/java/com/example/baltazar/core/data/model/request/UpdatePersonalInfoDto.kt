package com.example.baltazar.core.data.model.request

import com.example.baltazar.core.domain.model.PersonalInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonalInfoDto(
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("birthDate") val birthDate: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("address") val address: String? = null
)

fun PersonalInfo.toDto(): UpdatePersonalInfoDto {
    return UpdatePersonalInfoDto(
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
        gender = gender,
        address = address
    )
}
