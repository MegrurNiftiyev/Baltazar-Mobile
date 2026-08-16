package com.example.baltazar.core.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePassportRequestDto(
    @SerialName("passportNumber") val passportNumber: String,
    @SerialName("expiryDate") val expiryDate: String
)
