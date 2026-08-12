package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.RoadmapPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoadmapPointDto(
    @SerialName("lat") val lat: Double,
    @SerialName("long") val long: Double,
    @SerialName("order") val order: Int
) {
    fun toDomain(): RoadmapPoint = RoadmapPoint(lat = lat, long = long, order = order)
}
