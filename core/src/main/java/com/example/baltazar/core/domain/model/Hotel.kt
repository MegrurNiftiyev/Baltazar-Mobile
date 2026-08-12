package com.example.baltazar.core.domain.model

import com.example.baltazar.core.enums.CompanyStatus
import com.example.baltazar.core.enums.ServiceType

data class Hotel(
    override val id: String,
    val name: String,
    val about: String,
    val city: String,
    val address: String,
    val starRating: Int,
    val amenities: List<String>,
    override val images: List<String>,
    val logo: String,
    val serviceType: ServiceType,
    override val price: Double,
    val status: CompanyStatus,
    val rating: Double,
    val reviewCount: Int,
    val ratingSum: Int,
    override val createdAt: String
) : ServiceItem
