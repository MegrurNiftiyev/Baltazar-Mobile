package com.example.baltazar.core.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDetailsDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("about") val about: String? = null,
    @SerialName("serviceType") val serviceType: String,
    @SerialName("logo") val logo: String? = null,
    @SerialName("profileImage") val profileImage: String? = null,
    @SerialName("bannerImage") val bannerImage: String? = null,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("address") val address: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("reviewCount") val reviewCount: Int? = null,
    @SerialName("fullSectionOrder") val fullSectionOrder: List<String> = emptyList(),
    @SerialName("cuisineTypes") val cuisineTypes: List<String>? = null
)
