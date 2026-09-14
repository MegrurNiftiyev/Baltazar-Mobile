package com.example.baltazar.feature.company.data.model.dto

import com.example.baltazar.feature.company.domain.model.Company
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyListItemDto(
    @SerialName("id") val id: String? = null,
    @SerialName("_id") val mongoId: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("about") val about: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("serviceType") val serviceType: String? = null,
    @SerialName("profileImage") val profileImage: String? = null,
    @SerialName("logo") val logo: String? = null,
    @SerialName("bannerImage") val bannerImage: String? = null,
    @SerialName("coverImage") val coverImage: String? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("reviewCount") val reviewCount: Int? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("address") val address: String? = null
) {
    fun toDomain(): Company {
        return Company(
            id = id ?: mongoId ?: "",
            name = name.orEmpty(),
            logoUrl = profileImage ?: logo,
            coverImageUrl = bannerImage ?: coverImage,
            rating = rating ?: 0.0,
            reviewCount = reviewCount ?: 0,
            category = category ?: serviceType.orEmpty(),
            description = about ?: description,
            address = address
        )
    }
}
