package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.Car
import com.example.baltazar.core.domain.model.ServiceItem
import com.example.baltazar.core.enums.CarStatus
import com.example.baltazar.core.enums.FuelType
import com.example.baltazar.core.enums.TransmissionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String,
    @SerialName("brand") val brand: String,
    @SerialName("model") val model: String,
    @SerialName("year") val year: Int,
    @SerialName("category") val category: String,
    @SerialName("transmission") val transmission: TransmissionType,
    @SerialName("fuelType") val fuelType: FuelType,
    @SerialName("seats") val seats: Int,
    @SerialName("price") val price: Double,
    @SerialName("images") val images: List<String>,
    @SerialName("features") val features: List<String>,
    @SerialName("status") val status: CarStatus,
    @SerialName("rating") val rating: Double,
    @SerialName("reviewCount") val reviewCount: Int,
    @SerialName("ratingSum") val ratingSum: Int,
    @SerialName("createdAt") val createdAt: String
) {
    fun toDomain(): ServiceItem = Car(
        id = id,
        companyId = companyId,
        brand = brand,
        model = model,
        year = year,
        category = category,
        transmission = transmission,
        fuelType = fuelType,
        seats = seats,
        price = price,
        images = images,
        features = features,
        status = status,
        rating = rating,
        reviewCount = reviewCount,
        ratingSum = ratingSum,
        createdAt = createdAt
    )
}
