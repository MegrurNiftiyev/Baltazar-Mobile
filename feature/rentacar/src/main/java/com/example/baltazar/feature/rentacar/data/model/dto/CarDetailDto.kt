package com.example.baltazar.feature.rentacar.data.model.dto

import com.example.baltazar.core.data.model.dto.ReviewEligibilityDto
import com.example.baltazar.core.domain.model.ReviewEligibility
import com.example.baltazar.feature.rentacar.domain.model.CarDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarDetailDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String? = null,
    @SerialName("brand") val brand: String? = null,
    @SerialName("model") val model: String? = null,
    @SerialName("year") val year: Int = 0,
    @SerialName("category") val category: String? = null,
    @SerialName("transmission") val transmission: String? = null,
    @SerialName("fuelType") val fuelType: String? = null,
    @SerialName("seats") val seats: Int = 0,
    @SerialName("price") val price: Double = 0.0,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("features") val features: List<String> = emptyList(),
    @SerialName("status") val status: String? = null,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("priceSuffix") val priceSuffix: String? = "/ gün",
    @SerialName("reviewEligibility") val reviewEligibility: ReviewEligibilityDto? = null
) {
    fun toDomain(): CarDetail = CarDetail(
        id = id,
        companyId = companyId.orEmpty(),
        brand = brand.orEmpty(),
        model = model.orEmpty(),
        year = year,
        category = category.orEmpty(),
        transmission = transmission.orEmpty(),
        fuelType = fuelType.orEmpty(),
        seats = seats,
        price = price,
        images = images,
        features = features,
        status = status.orEmpty(),
        rating = rating,
        reviewCount = reviewCount,
        priceSuffix = if (priceSuffix.isNullOrBlank()) "/ gün" else priceSuffix,
        reviewEligibility = reviewEligibility?.toDomain() ?: ReviewEligibility()
    )
}
