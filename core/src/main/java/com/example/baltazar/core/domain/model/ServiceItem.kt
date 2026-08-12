package com.example.baltazar.core.domain.model

sealed interface ServiceItem {
    val id: String
    val price: Double
    val images: List<String>
    val createdAt: String
}
