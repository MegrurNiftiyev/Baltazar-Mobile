package com.example.baltazar.feature.explore.domain.model

import com.example.baltazar.core.enums.ServiceType

data class ExploreItem(
    val id: String,
    val serviceType: ServiceType,
    val serviceId: String,
    val title: String,
    val image: String,
    val price: Double,
    val priceSuffix: String,
    val currency: String,
    val rating: Double,
    val ratingCount: Int,
    val category: String?
)
