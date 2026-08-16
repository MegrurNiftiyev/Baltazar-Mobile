package com.example.baltazar.feature.rentacar.domain.model

data class CarFilter(
    val companyId: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val category: String? = null,
    val transmission: String? = null,
    val fuelType: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)
