package com.example.baltazar.core.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonalInfoRequestDto(
    @SerialName("dateOfBirth") val dateOfBirth: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("idNumber") val idNumber: String? = null
)
