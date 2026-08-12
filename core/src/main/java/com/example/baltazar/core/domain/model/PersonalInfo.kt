package com.example.baltazar.core.domain.model

data class PersonalInfo(
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: String,
    val address: String? = null
)
