package com.example.baltazar.feature.rentacar.data.model.request

import com.example.baltazar.feature.rentacar.domain.model.CarFilter
import kotlinx.serialization.Serializable

@Serializable
data class CarFilterRequest(
    val companyId: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val category: String? = null,
    val transmission: String? = null,
    val fuelType: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)

fun CarFilter.toRequest(): CarFilterRequest = CarFilterRequest(
    companyId = companyId,
    brand = brand,
    model = model,
    category = category,
    transmission = transmission,
    fuelType = fuelType,
    minPrice = minPrice,
    maxPrice = maxPrice,
    limit = limit,
    cursor = cursor
)
