package com.example.baltazar.feature.food.data.model.dto

import com.example.baltazar.core.data.model.dto.PaginationDto
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.food.domain.model.FoodItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodItemDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String,
    @SerialName("title") val title: String,
    @SerialName("categories") val categories: List<String> = emptyList(),
    @SerialName("price") val price: Double,
    @SerialName("priceSuffix") val priceSuffix: String,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("image") val image: String
) {
    fun toDomain(): FoodItem = FoodItem(
        id = id,
        companyId = companyId,
        title = title,
        categories = categories,
        price = price,
        priceSuffix = priceSuffix,
        rating = rating,
        reviewCount = reviewCount,
        image = image
    )
}

@Serializable
data class FoodListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<FoodItemDto> = emptyList(),
    @SerialName("pagination") val pagination: PaginationDto
) {
    fun toDomain(): PaginatedList<FoodItem> = PaginatedList(
        items = data.map { it.toDomain() },
        pagination = pagination.toDomain()
    )
}
