package com.example.baltazar.feature.explore.data.model.response

import com.example.baltazar.feature.explore.data.model.dto.BannerItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<BannerItemDto>? = null
)
