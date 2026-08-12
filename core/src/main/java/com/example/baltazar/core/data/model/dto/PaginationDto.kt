package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.PaginationInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto(
    @SerialName("nextCursor") val nextCursor: String? = null,
    @SerialName("hasMore") val hasMore: Boolean = false,
    @SerialName("limit") val limit: Int = 20
) {
    fun toDomain(): PaginationInfo = PaginationInfo(
        nextCursor = nextCursor,
        hasMore = hasMore,
        limit = limit
    )
}
