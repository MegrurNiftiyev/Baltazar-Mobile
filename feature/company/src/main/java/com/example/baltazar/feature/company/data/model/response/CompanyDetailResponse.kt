package com.example.baltazar.feature.company.data.model.response

import com.example.baltazar.feature.company.data.model.dto.CompanyDetailsDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDetailResponse(
    @SerialName("success") val success: Boolean? = true,
    @SerialName("message") val message: String? = null,
    @SerialName("data") val data: CompanyDetailsDto? = null
)
