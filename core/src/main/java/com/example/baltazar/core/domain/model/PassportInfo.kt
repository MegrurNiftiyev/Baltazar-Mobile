package com.example.baltazar.core.domain.model

data class PassportInfo(
    val passportNumber: String,
    val citizenship: String,
    val issueDate: String,
    val expiryDate: String
)
