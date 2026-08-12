package com.example.baltazar.core.data.model.request

import com.example.baltazar.core.domain.model.DriverLicenseInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDriverLicenseDto(
    @SerialName("licenseNumber") val licenseNumber: String,
    @SerialName("category") val category: String,
    @SerialName("issueDate") val issueDate: String? = null,
    @SerialName("expiryDate") val expiryDate: String? = null
)

fun DriverLicenseInfo.toDto(): UpdateDriverLicenseDto {
    return UpdateDriverLicenseDto(
        licenseNumber = licenseNumber,
        category = category,
        issueDate = issueDate,
        expiryDate = expiryDate
    )
}
