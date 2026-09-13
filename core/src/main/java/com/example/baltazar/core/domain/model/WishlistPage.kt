package com.example.baltazar.core.domain.model

data class WishlistPage(
    val items: List<WishlistItem> = emptyList(),
    val hasMore: Boolean = false,
    val nextCursor: String? = null
)
