package com.example.baltazar.feature.company.data.model.response

import com.example.baltazar.feature.company.data.model.dto.CompanyListItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyListResponse(
    @SerialName("success") val success: Boolean? = true,
    @SerialName("message") val message: String? = null,
    @SerialName("data") val data: List<CompanyListItemDto>? = null,
    @SerialName("items") val items: List<CompanyListItemDto>? = null
)
