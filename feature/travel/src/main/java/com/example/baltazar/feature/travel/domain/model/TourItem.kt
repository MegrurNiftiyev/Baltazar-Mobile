package com.example.baltazar.feature.travel.domain.model

data class TourItem(
    val id: String,
    val companyId: String,
    val title: String,
    val categories: List<String>,
    val price: Double,
    val priceSuffix: String,
    val image: String,
    val rating: Double,
    val reviewCount: Int,
    val duration: String,
    val startDate: String,
    val endDate: String,
    val status: String
)


