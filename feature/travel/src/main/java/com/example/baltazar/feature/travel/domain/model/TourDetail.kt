package com.example.baltazar.feature.travel.domain.model

import com.example.baltazar.core.domain.model.ReviewEligibility

data class TourDetail(
    val id: String = "",
    val companyId: String = "",
    val title: String = "",
    val categories: List<String> = emptyList(),
    val roadmap: List<TourRoadmapPoint> = emptyList(),
    val images: List<String> = emptyList(),
    val duration: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val includedServices: List<String> = emptyList(),
    val price: Double = 0.0,
    val status: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val priceSuffix: String = "paket qiyməti",
    val reviewEligibility: ReviewEligibility = ReviewEligibility()
)
