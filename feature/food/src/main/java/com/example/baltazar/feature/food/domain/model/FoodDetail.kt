package com.example.baltazar.feature.food.domain.model

import com.example.baltazar.core.domain.model.ReviewEligibility

data class FoodDetail(
    val id: String = "",
    val companyId: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
    val status: String = "",
    val calories: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val carb: Int = 0,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val priceSuffix: String = "",
    val reviewEligibility: ReviewEligibility = ReviewEligibility()
)
