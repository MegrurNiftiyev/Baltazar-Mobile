package com.example.baltazar.feature.food.data.model.dto

import com.example.baltazar.core.data.model.dto.ReviewEligibilityDto
import com.example.baltazar.core.domain.model.ReviewEligibility
import com.example.baltazar.feature.food.domain.model.FoodDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodDetailDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("price") val price: Double = 0.0,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("ingredients") val ingredients: List<String> = emptyList(),
    @SerialName("status") val status: String? = null,
    @SerialName("calories") val calories: Int = 0,
    @SerialName("protein") val protein: Int = 0,
    @SerialName("fat") val fat: Int = 0,
    @SerialName("carb") val carb: Int = 0,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("priceSuffix") val priceSuffix: String? = "",
    @SerialName("reviewEligibility") val reviewEligibility: ReviewEligibilityDto? = null
) {
    fun toDomain(): FoodDetail = FoodDetail(
        id = id,
        companyId = companyId.orEmpty(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        category = category.orEmpty(),
        price = price,
        images = images,
        ingredients = ingredients,
        status = status.orEmpty(),
        calories = calories,
        protein = protein,
        fat = fat,
        carb = carb,
        rating = rating,
        reviewCount = reviewCount,
        priceSuffix = priceSuffix ?: "",
        reviewEligibility = reviewEligibility?.toDomain() ?: ReviewEligibility()
    )
}
