package com.example.baltazar.feature.explore.data.model.response

import com.example.baltazar.feature.explore.data.model.dto.ExploreSectionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<ExploreSectionDto>? = null
)
