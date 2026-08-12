package com.example.baltazar.core.domain.model

import com.example.baltazar.core.enums.CompanyStatus

data class Travel(
    override val id: String,
    val companyId: String,
    val categories: List<String>,
    val title: String,
    val roadmap: List<RoadmapPoint>,
    override val images: List<String>,
    val duration: String,
    val startDate: String,
    val endDate: String,
    val includedServices: List<String>,
    override val price: Double,
    val status: CompanyStatus,
    val rating: Double?,
    val reviewCount: Int?,
    val ratingSum: Int?,
    override val createdAt: String
) : ServiceItem
