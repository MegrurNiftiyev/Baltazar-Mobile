package com.example.baltazar.core.domain.model

import com.example.baltazar.core.enums.ServiceType

data class WishlistItem(
    val wishlistItemId: String,
    val serviceType: ServiceType,
    val serviceId: String,
    val title: String,
    val image: String,
    val price: Double,
    val priceSuffix: String,
    val currency: String = "AZN",
    val rating: Double = 0.0,
    val ratingCount: Int = 0,
    val category: String? = null
)

data class WishlistPage(
    val items: List<WishlistItem> = emptyList(),
    val hasMore: Boolean = false,
    val nextCursor: String? = null
)

data class AddToWishlistInfo(
    val serviceId: String,
    val serviceType: ServiceType
)
