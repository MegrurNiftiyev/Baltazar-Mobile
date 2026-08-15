package com.example.baltazar.feature.travel.data.model.request

import com.example.baltazar.feature.travel.domain.model.TourFilter
import kotlinx.serialization.Serializable

@Serializable
data class TourFilterRequest(
    val companyId: String? = null,
    val category: String? = null,
    val minRating: Double? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val name: String? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)

fun TourFilter.toRequest(): TourFilterRequest = TourFilterRequest(
    companyId = companyId,
    category = category,
    minRating = minRating,
    startDate = startDate,
    endDate = endDate,
    name = name,
    limit = limit,
    cursor = cursor
)
