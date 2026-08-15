package com.example.baltazar.feature.rentacar.domain.model

import com.example.baltazar.core.domain.model.ReviewEligibility

data class CarDetail(
    val id: String = "",
    val companyId: String = "",
    val brand: String = "",
    val model: String = "",
    val year: Int = 0,
    val category: String = "",
    val transmission: String = "",
    val fuelType: String = "",
    val seats: Int = 0,
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
    val features: List<String> = emptyList(),
    val status: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val priceSuffix: String = "/ gün",
    val reviewEligibility: ReviewEligibility = ReviewEligibility()
)
