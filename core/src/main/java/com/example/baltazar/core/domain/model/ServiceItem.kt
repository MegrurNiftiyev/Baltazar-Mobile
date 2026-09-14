package com.example.baltazar.core.domain.model

data class ServiceItem(
    val id: String = "",
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
    val createdAt: String = ""
)
