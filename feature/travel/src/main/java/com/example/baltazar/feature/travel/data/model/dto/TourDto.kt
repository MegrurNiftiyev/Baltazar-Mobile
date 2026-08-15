package com.example.baltazar.feature.travel.data.model.dto

import com.example.baltazar.feature.travel.domain.model.TourItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TourDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("categories") val categories: List<String> = emptyList(),
    @SerialName("price") val price: Double = 0.0,
    @SerialName("priceSuffix") val priceSuffix: String? = "/ person",
    @SerialName("image") val image: String? = null,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("duration") val duration: String? = null,
    @SerialName("startDate") val startDate: String? = null,
    @SerialName("endDate") val endDate: String? = null,
    @SerialName("status") val status: String? = "ACTIVE"
) {
    fun toDomain(): TourItem {
        return TourItem(
            id = id,
            companyId = companyId.orEmpty(),
            title = title.orEmpty(),
            categories = categories,
            price = price,
            priceSuffix = priceSuffix ?: "/ person",
            image = image.orEmpty(),
            rating = rating,
            reviewCount = reviewCount,
            duration = duration.orEmpty(),
            startDate = startDate.orEmpty(),
            endDate = endDate.orEmpty(),
            status = status ?: "ACTIVE"
        )
    }
}
