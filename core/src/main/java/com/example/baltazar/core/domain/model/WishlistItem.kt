package com.example.baltazar.core.domain.model

import com.example.baltazar.core.core.enums.ServiceType

data class WishlistItem(
    val wishlistItemId: String = "",
    val serviceType: ServiceType = ServiceType.UNKNOWN,
    val serviceId: String = "",
    val title: String = "",
    val image: String = "",
    val price: Double = 0.0,
    val priceSuffix: String = "",
    val currency: String = "AZN",
    val rating: Double = 0.0,
    val ratingCount: Int = 0,
    val category: String = "",
    val isLiked: Boolean = true
)

