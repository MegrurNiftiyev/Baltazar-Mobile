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
    val personalInfo: Boolean = false,
    val driverLicense: Boolean = false,
    val passport: Boolean = false,
    val createdAt: String
)
