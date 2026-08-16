package com.example.baltazar.feature.food.data.model.dto

import com.example.baltazar.feature.food.domain.model.FoodItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodItemDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("categories") val categories: List<String> = emptyList(),
    @SerialName("price") val price: Double = 0.0,
    @SerialName("priceSuffix") val priceSuffix: String? = "",
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("image") val image: String? = null
) {
    fun toDomain(): FoodItem {
        val resolvedCategories = if (categories.isNotEmpty()) {
            categories
        } else if (!category.isNullOrBlank()) {
            listOf(category)
        } else {
            emptyList()
        }

        return FoodItem(
            id = id,
            companyId = companyId.orEmpty(),
            title = title ?: name.orEmpty(),
            categories = resolvedCategories,
            price = price,
            priceSuffix = priceSuffix ?: "",
            rating = rating,
            reviewCount = reviewCount,
            image = image.orEmpty()
        )
    }
}
