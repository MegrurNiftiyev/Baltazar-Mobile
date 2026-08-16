package com.example.baltazar.feature.travel.domain.model

data class TourFilter(
    val companyId: String? = null,
    val category: String? = null,
    val minRating: Double? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val name: String? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)
