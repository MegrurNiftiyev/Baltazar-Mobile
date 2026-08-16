package com.example.baltazar.feature.company.domain.model

data class RelatedItem(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val price: Double,
    val rating: Double,
    val category: String?,
    val priceSuffix: String? = null,
    val currency: String = "AZN",
    val reviewCount: Int = 0
)
