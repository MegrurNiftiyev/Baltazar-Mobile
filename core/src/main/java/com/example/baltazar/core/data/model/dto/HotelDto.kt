package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.Hotel
import com.example.baltazar.core.domain.model.ServiceItem
import com.example.baltazar.core.enums.CompanyStatus
import com.example.baltazar.core.enums.ServiceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotelDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("about") val about: String,
    @SerialName("city") val city: String,
    @SerialName("address") val address: String,
    @SerialName("starRating") val starRating: Int,
    @SerialName("amenities") val amenities: List<String>,
    @SerialName("images") val images: List<String>,
    @SerialName("logo") val logo: String,
    @SerialName("serviceType") val serviceType: ServiceType,
    @SerialName("price") val price: Double,
    @SerialName("status") val status: CompanyStatus,
    @SerialName("rating") val rating: Double,
    @SerialName("reviewCount") val reviewCount: Int,
    @SerialName("ratingSum") val ratingSum: Int,
    @SerialName("createdAt") val createdAt: String
) {
    fun toDomain(): ServiceItem = Hotel(
        id = id,
        name = name,
        about = about,
        city = city,
        address = address,
        starRating = starRating,
        amenities = amenities,
        images = images,
        logo = logo,
        serviceType = serviceType,
        price = price,
        status = status,
        rating = rating,
        reviewCount = reviewCount,
        ratingSum = ratingSum,
        createdAt = createdAt
    )
}
