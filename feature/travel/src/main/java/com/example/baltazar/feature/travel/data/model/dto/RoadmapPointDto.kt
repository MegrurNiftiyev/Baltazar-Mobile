package com.example.baltazar.feature.travel.data.model.dto

import com.example.baltazar.feature.travel.domain.model.TourRoadmapPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoadmapPointDto(
    @SerialName("lat") val lat: Double = 0.0,
    @SerialName("long") val long: Double = 0.0,
    @SerialName("order") val order: Int = 0,
    @SerialName("name") val name: String? = null
) {
    fun toDomain(): TourRoadmapPoint = TourRoadmapPoint(
        lat = lat,
        long = long,
        order = order,
        name = name ?: "Məntəqə $order"
    )
}
