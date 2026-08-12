package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.Food
import com.example.baltazar.core.domain.model.ServiceItem
import com.example.baltazar.core.enums.FoodItemStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("category") val category: String,
    @SerialName("price") val price: Double,
    @SerialName("images") val images: List<String>,
    @SerialName("ingredients") val ingredients: List<String>,
    @SerialName("status") val status: FoodItemStatus,
    @SerialName("calories") val calories: Int,
    @SerialName("protein") val protein: Int,
    @SerialName("fat") val fat: Int,
    @SerialName("carb") val carb: Int,
    @SerialName("createdAt") val createdAt: String
) {
    fun toDomain(): ServiceItem = Food(
        id = id,
        companyId = companyId,
        name = name,
        description = description,
        category = category,
        price = price,
        images = images,
        ingredients = ingredients,
        status = status,
        calories = calories,
        protein = protein,
        fat = fat,
        carb = carb,
        createdAt = createdAt
    )
}
