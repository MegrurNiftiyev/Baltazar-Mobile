package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.IncludedService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncludedServiceDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("serviceType") val serviceType: String,
    @SerialName("createdAt") val createdAt: String? = null
) {
    fun toDomain(): IncludedService = IncludedService(
        id = id,
        name = name,
        serviceType = serviceType
    )
}
