package com.example.baltazar.core.domain.model

import com.example.baltazar.core.enums.CarStatus
import com.example.baltazar.core.enums.FuelType
import com.example.baltazar.core.enums.TransmissionType

data class Car(
    override val id: String,
    val companyId: String,
    val brand: String,
    val model: String,
    val year: Int,
    val category: String,
    val transmission: TransmissionType,
    val fuelType: FuelType,
    val seats: Int,
    override val price: Double,
    override val images: List<String>,
    val features: List<String>,
    val status: CarStatus,
    val rating: Double,
    val reviewCount: Int,
    val ratingSum: Int,
    override val createdAt: String
) : ServiceItem
