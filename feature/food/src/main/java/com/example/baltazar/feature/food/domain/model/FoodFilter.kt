package com.example.baltazar.feature.food.domain.model

data class FoodFilter(
    val companyId: String? = null,
    val category: String? = null,
    val name: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)
