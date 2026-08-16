package com.example.baltazar.feature.food.data.model.request

import com.example.baltazar.feature.food.domain.model.FoodFilter
import kotlinx.serialization.Serializable

@Serializable
data class FoodFilterRequest(
    val companyId: String? = null,
    val category: String? = null,
    val name: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)

fun FoodFilter.toRequest(): FoodFilterRequest = FoodFilterRequest(
    companyId = companyId,
    category = category,
    name = name,
    minPrice = minPrice,
    maxPrice = maxPrice,
    limit = limit,
    cursor = cursor
)
