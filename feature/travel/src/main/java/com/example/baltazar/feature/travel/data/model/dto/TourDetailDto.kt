package com.example.baltazar.feature.travel.data.model.dto

import com.example.baltazar.core.data.model.dto.ReviewEligibilityDto
import com.example.baltazar.core.domain.model.ReviewEligibility
import com.example.baltazar.feature.travel.domain.model.TourDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TourDetailDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("categories") val categories: List<String> = emptyList(),
    @SerialName("roadmap") val roadmap: List<RoadmapPointDto> = emptyList(),
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("duration") val duration: String? = null,
    @SerialName("startDate") val startDate: String? = null,
    @SerialName("endDate") val endDate: String? = null,
    @SerialName("includedServices") val includedServices: List<String> = emptyList(),
    @SerialName("price") val price: Double = 0.0,
    @SerialName("status") val status: String? = null,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("priceSuffix") val priceSuffix: String? = "paket qiyməti",
    @SerialName("reviewEligibility") val reviewEligibility: ReviewEligibilityDto? = null
) {
    fun toDomain(): TourDetail = TourDetail(
        id = id,
        companyId = companyId.orEmpty(),
        title = title.orEmpty(),
        categories = categories,
        roadmap = roadmap.map { it.toDomain() }.sortedBy { it.order },
        images = images,
        duration = duration.orEmpty(),
        startDate = startDate.orEmpty(),
        endDate = endDate.orEmpty(),
        includedServices = includedServices,
        price = price,
        status = status.orEmpty(),
        rating = rating,
        reviewCount = reviewCount,
        priceSuffix = if (priceSuffix.isNullOrBlank()) "paket qiyməti" else priceSuffix,
        reviewEligibility = reviewEligibility?.toDomain() ?: ReviewEligibility()
    )
}
