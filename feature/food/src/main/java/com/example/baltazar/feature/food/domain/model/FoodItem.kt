package com.example.baltazar.feature.food.domain.model

data class FoodItem(
    val id: String,
    val companyId: String,
    val title: String,
    val categories: List<String> = emptyList(),
    val price: Double,
    val priceSuffix: String,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val image: String
)
