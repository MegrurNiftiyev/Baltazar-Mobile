package com.example.baltazar.feature.rentacar.domain.model

data class CarItem(
    val id: String,
    val companyId: String,
    val title: String,
    val category: String,
    val price: Double,
    val priceSuffix: String,
    val image: String,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val transmission: String,
    val fuelType: String
)
