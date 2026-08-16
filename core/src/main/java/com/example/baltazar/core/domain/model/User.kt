package com.example.baltazar.core.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String? = null,
    val region: String? = null,
    val language: String = "en",
    val avatarUrl: String? = null,
    val personalInfoCompleted: Boolean = false,
    val driverLicenseCompleted: Boolean = false,
    val passportCompleted: Boolean = false,
    val createdAt: String
) {
    val personalInfo: Boolean get() = personalInfoCompleted
    val driverLicense: Boolean get() = driverLicenseCompleted
    val passport: Boolean get() = passportCompleted
}
