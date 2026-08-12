package com.example.baltazar.feature.travel.data.model.dto

import com.example.baltazar.core.data.model.dto.PaginationDto
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.travel.domain.model.TourItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TourItemDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String,
    @SerialName("title") val title: String,
    @SerialName("categories") val categories: List<String> = emptyList(),
    @SerialName("price") val price: Double,
    @SerialName("priceSuffix") val priceSuffix: String,
    @SerialName("image") val image: String,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("duration") val duration: String,
    @SerialName("startDate") val startDate: String,
    @SerialName("endDate") val endDate: String,
    @SerialName("status") val status: String
) {
    fun toDomain(): TourItem = TourItem(
        id = id,
        companyId = companyId,
        title = title,
        categories = categories,
        price = price,
        priceSuffix = priceSuffix,
        image = image,
        rating = rating,
        reviewCount = reviewCount,
        duration = duration,
        startDate = startDate,
        endDate = endDate,
        status = status
    )
}

@Serializable
data class TourListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<TourItemDto> = emptyList(),
    @SerialName("pagination") val pagination: PaginationDto
) {
    fun toDomain(): PaginatedList<TourItem> = PaginatedList(
        items = data.map { it.toDomain() },
        pagination = pagination.toDomain()
    )
}
