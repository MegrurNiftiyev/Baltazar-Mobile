package com.example.baltazar.feature.order.domain.model

data class OrderPersonalInfo(
    val name: String,
    val phone: String,
    val dateOfBirth: String?,
    val address: String?,
    val idNumber: String?
)
