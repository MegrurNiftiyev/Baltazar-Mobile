package com.example.baltazar.feature.company.data.model.dto

import com.example.baltazar.feature.company.domain.model.CompanyDetail
import com.example.baltazar.feature.company.domain.model.CompanySectionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDetailsDto(
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
    @SerialName("images") val images: List<String>? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("reviewCount") val reviewCount: Int? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("workingHours") val workingHours: String? = null,
    @SerialName("sectionOrder") val sectionOrder: List<String>? = null,
    @SerialName("fullSectionOrder") val fullSectionOrder: List<String>? = null
) {
    fun toDomain(): CompanyDetail {
        val imageList = mutableListOf<String>()
        val mainCover = bannerImage ?: coverImage
        val mainProfile = profileImage ?: logo

        mainCover?.takeIf { it.isNotBlank() }?.let { imageList.add(it) }
        mainProfile?.takeIf { it.isNotBlank() && !imageList.contains(it) }?.let { imageList.add(it) }
        images?.filter { it.isNotBlank() }?.forEach { img ->
            if (!imageList.contains(img)) imageList.add(img)
        }

        val rawSections = fullSectionOrder ?: sectionOrder ?: listOf("HEADER", "ABOUT", "ITEMS", "REVIEWS")
        val parsedSections = rawSections.map { CompanySectionType.fromString(it) }
            .filter { it != CompanySectionType.UNKNOWN }
            .ifEmpty {
                listOf(
                    CompanySectionType.HEADER,
                    CompanySectionType.ABOUT,
                    CompanySectionType.ITEMS,
                    CompanySectionType.REVIEWS
                )
            }

        return CompanyDetail(
            id = id ?: mongoId ?: "",
            name = name.orEmpty(),
            about = about ?: description.orEmpty(),
            serviceType = serviceType.orEmpty(),
            logoUrl = mainProfile,
            coverImageUrl = mainCover,
            images = imageList,
            rating = rating ?: 0.0,
            reviewCount = reviewCount ?: 0,
            category = category ?: serviceType.orEmpty(),
            address = address.orEmpty(),
            phone = phone,
            email = email,
            website = website,
            workingHours = workingHours,
            sectionOrder = parsedSections
        )
    }
}
