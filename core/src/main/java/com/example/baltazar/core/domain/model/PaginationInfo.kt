package com.example.baltazar.core.domain.model

data class PaginationInfo(
    val nextCursor: String?,
    val hasMore: Boolean,
    val limit: Int
)
