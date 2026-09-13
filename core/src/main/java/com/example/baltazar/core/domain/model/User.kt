package com.example.baltazar.core.domain.model

import com.example.baltazar.core.core.enums.UserRole

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole = UserRole.Guest,
    val phone: String? = null,
    val region: String? = null,
    val language: String = "en",
    val avatarUrl: String? = null,
    val personalInfoCompleted: Boolean = false,
    val driverLicenseCompleted: Boolean = false,
    val passportCompleted: Boolean = false,
    val createdAt: String
) {
    val isGuest: Boolean get() = role == UserRole.Guest || id == "guest"
    val personalInfo: Boolean get() = personalInfoCompleted
    val driverLicense: Boolean get() = driverLicenseCompleted
    val passport: Boolean get() = passportCompleted
}
