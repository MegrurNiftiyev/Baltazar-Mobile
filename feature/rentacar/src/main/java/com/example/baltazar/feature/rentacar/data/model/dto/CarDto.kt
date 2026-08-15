package com.example.baltazar.feature.rentacar.data.model.dto

import com.example.baltazar.feature.rentacar.domain.model.CarItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("brand") val brand: String? = null,
    @SerialName("model") val model: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("price") val price: Double = 0.0,
    @SerialName("priceSuffix") val priceSuffix: String? = "/ day",
    @SerialName("image") val image: String? = null,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("transmission") val transmission: String? = null,
    @SerialName("fuelType") val fuelType: String? = null
) {
    fun toDomain(): CarItem {
        val carTitle = when {
            !title.isNullOrBlank() -> title
            !brand.isNullOrBlank() && !model.isNullOrBlank() -> "$brand $model"
            !brand.isNullOrBlank() -> brand
            !model.isNullOrBlank() -> model
            else -> "Car"
        }

        return CarItem(
            id = id,
            companyId = companyId.orEmpty(),
            title = carTitle,
            category = category.orEmpty(),
            price = price,
            priceSuffix = priceSuffix ?: "/ day",
            image = image.orEmpty(),
            rating = rating,
            reviewCount = reviewCount,
            transmission = transmission.orEmpty(),
            fuelType = fuelType.orEmpty()
        )
    }
}
