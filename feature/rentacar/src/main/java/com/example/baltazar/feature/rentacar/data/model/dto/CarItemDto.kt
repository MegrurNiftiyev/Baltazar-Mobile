package com.example.baltazar.feature.rentacar.data.model.dto

import com.example.baltazar.core.data.model.dto.PaginationDto
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.rentacar.domain.model.CarItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarItemDto(
    @SerialName("id") val id: String,
    @SerialName("companyId") val companyId: String,
    @SerialName("title") val title: String,
    @SerialName("category") val category: String,
    @SerialName("price") val price: Double,
    @SerialName("priceSuffix") val priceSuffix: String,
    @SerialName("image") val image: String,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("transmission") val transmission: String,
    @SerialName("fuelType") val fuelType: String
) {
    fun toDomain(): CarItem = CarItem(
        id = id,
        companyId = companyId,
        title = title,
        category = category,
        price = price,
        priceSuffix = priceSuffix,
        image = image,
        rating = rating,
        reviewCount = reviewCount,
        transmission = transmission,
        fuelType = fuelType
    )
}

@Serializable
data class CarListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<CarItemDto> = emptyList(),
    @SerialName("pagination") val pagination: PaginationDto
) {
    fun toDomain(): PaginatedList<CarItem> = PaginatedList(
        items = data.map { it.toDomain() },
        pagination = pagination.toDomain()
    )
}
