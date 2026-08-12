package com.example.baltazar.core.domain.model

import com.example.baltazar.core.enums.FoodItemStatus

data class Food(
    override val id: String,
    val companyId: String,
    val name: String,
    val description: String,
    val category: String,
    override val price: Double,
    override val images: List<String>,
    val ingredients: List<String>,
    val status: FoodItemStatus,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carb: Int,
    override val createdAt: String
) : ServiceItem
