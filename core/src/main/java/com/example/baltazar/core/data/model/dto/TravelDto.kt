package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.ServiceItem
import com.example.baltazar.core.domain.model.Travel
import com.example.baltazar.core.enums.CompanyStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TravelDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String,
    @SerialName("categories") val categories: List<String>,
    @SerialName("title") val title: String,
    @SerialName("roadmap") val roadmap: List<RoadmapPointDto>,
    @SerialName("images") val images: List<String>,
    @SerialName("duration") val duration: String,
    @SerialName("startDate") val startDate: String,
    @SerialName("endDate") val endDate: String,
    @SerialName("includedServices") val includedServices: List<String>,
    @SerialName("price") val price: Double,
    @SerialName("status") val status: CompanyStatus,
    @SerialName("rating") val rating: Double?,
    @SerialName("reviewCount") val reviewCount: Int?,
    @SerialName("ratingSum") val ratingSum: Int?,
    @SerialName("createdAt") val createdAt: String
) {
    fun toDomain(): ServiceItem = Travel(
        id = id,
        companyId = companyId,
        categories = categories,
        title = title,
        roadmap = roadmap.map { it.toDomain() },
        images = images,
        duration = duration,
        startDate = startDate,
        endDate = endDate,
        includedServices = includedServices,
        price = price,
        status = status,
        rating = rating,
        reviewCount = reviewCount,
        ratingSum = ratingSum,
        createdAt = createdAt
    )
}
