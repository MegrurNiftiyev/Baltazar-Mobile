package com.example.baltazar.core.domain.model

data class PaginationInfo(
    val nextCursor: String? = null,
    val hasMore: Boolean = false,
    val limit: Int = 20
)
