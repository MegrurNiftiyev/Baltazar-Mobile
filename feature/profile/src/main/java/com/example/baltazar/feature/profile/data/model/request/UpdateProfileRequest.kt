package com.example.baltazar.feature.profile.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @SerialName("name") val name: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("personalInfo") val personalInfo: PersonalInfoDetailsDto? = null,
    @SerialName("driverLicense") val driverLicense: DriverLicenseDetailsDto? = null,
    @SerialName("passport") val passport: PassportDetailsDto? = null
)

@Serializable
data class PersonalInfoDetailsDto(
    @SerialName("dateOfBirth") val dateOfBirth: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("idNumber") val idNumber: String? = null
)

@Serializable
data class DriverLicenseDetailsDto(
    @SerialName("licenseNumber") val licenseNumber: String,
    @SerialName("expiryDate") val expiryDate: String
)

@Serializable
data class PassportDetailsDto(
    @SerialName("passportNumber") val passportNumber: String,
    @SerialName("expiryDate") val expiryDate: String
)
