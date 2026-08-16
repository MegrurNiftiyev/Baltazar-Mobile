package com.example.baltazar.feature.company.domain.model

data class Company(
    val id: String,
    val name: String,
    val logoUrl: String?,
    val coverImageUrl: String?,
    val rating: Double,
    val reviewCount: Int,
    val category: String,
    val description: String?,
    val address: String?
)
