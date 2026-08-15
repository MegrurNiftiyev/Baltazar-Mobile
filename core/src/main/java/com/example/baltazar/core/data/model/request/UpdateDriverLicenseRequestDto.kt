package com.example.baltazar.core.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDriverLicenseRequestDto(
    @SerialName("licenseNumber") val licenseNumber: String,
    @SerialName("expiryDate") val expiryDate: String
)
