package com.example.baltazar.feature.auth.data.model.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestModel(
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val region: String,
    val language: String
)
