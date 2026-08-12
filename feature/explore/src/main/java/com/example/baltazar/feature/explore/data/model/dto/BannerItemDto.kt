package com.example.baltazar.feature.explore.data.model.dto

import com.example.baltazar.core.enums.ServiceType
import com.example.baltazar.feature.explore.domain.model.BannerItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerItemDto(
    @SerialName("id") val id: String,
    @SerialName("serviceType") val serviceType: ServiceType,
    @SerialName("title") val title: String,
    @SerialName("desc") val desc: String,
    @SerialName("order") val order: Int,
    @SerialName("isActive") val isActive: Boolean,
    @SerialName("image") val image: String,
    @SerialName("createdAt") val createdAt: String
) {
    fun toDomain(): BannerItem = BannerItem(
        id = id,
        serviceType = serviceType,
        title = title,
        desc = desc,
        order = order,
        isActive = isActive,
        image = image,
        createdAt = createdAt
    )
}
