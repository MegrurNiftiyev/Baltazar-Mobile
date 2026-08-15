package com.example.baltazar.feature.hotel.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceRangeDto(
    @SerialName("min") val min: Double = 0.0,
    @SerialName("max") val max: Double = 0.0
)
