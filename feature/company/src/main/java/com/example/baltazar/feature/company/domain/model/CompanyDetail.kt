package com.example.baltazar.feature.company.domain.model

data class CompanyDetail(
    val id: String,
    val name: String,
    val about: String,
    val serviceType: String,
    val logoUrl: String?,
    val coverImageUrl: String?,
    val images: List<String>,
    val rating: Double,
    val reviewCount: Int,
    val category: String,
    val address: String,
    val phone: String?,
    val email: String?,
    val website: String?,
    val workingHours: String?,
    val priceSuffix: String? = null,
    val sectionOrder: List<CompanySectionType> = listOf(
        CompanySectionType.HEADER,
        CompanySectionType.ABOUT,
        CompanySectionType.ITEMS,
        CompanySectionType.REVIEWS
    )
)
