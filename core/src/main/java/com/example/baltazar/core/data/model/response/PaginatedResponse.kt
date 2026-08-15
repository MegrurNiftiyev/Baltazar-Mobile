package com.example.baltazar.core.data.model.response

import com.example.baltazar.core.data.model.dto.PaginationDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<T> = emptyList(),
    @SerialName("pagination") val pagination: PaginationDto? = null,
    @SerialName("message") val message: String? = null
)
