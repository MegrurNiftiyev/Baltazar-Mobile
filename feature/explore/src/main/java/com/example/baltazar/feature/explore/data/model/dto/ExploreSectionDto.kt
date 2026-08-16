package com.example.baltazar.feature.explore.data.model.dto

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.data.model.dto.ServiceCardItemDto
import com.example.baltazar.feature.explore.domain.model.ExploreSection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreSectionDto(
    @SerialName("serviceType") val serviceType: ServiceType,
    @SerialName("title") val title: String,
    @SerialName("order") val order: Int,
    @SerialName("items") val items: List<ServiceCardItemDto> = emptyList()
) {
    fun toDomain(): ExploreSection = ExploreSection(
        serviceType = serviceType,
        title = title,
        items = items.map { it.toDomain() },
        order = order
    )
}
