package com.example.baltazar.feature.order.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NominatimReverseResultDto(
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("lat") val lat: String? = null,
    @SerialName("lon") val lon: String? = null
)
