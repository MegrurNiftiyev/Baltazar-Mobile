package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.WishlistItem
import com.example.baltazar.core.domain.model.WishlistPage
import com.example.baltazar.core.enums.ServiceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WishlistItemDto(
    @SerialName("wishlistItemId") val wishlistItemId: String,
    @SerialName("serviceType") val serviceType: ServiceType,
    @SerialName("serviceId") val serviceId: String,
    @SerialName("title") val title: String,
    @SerialName("image") val image: String,
    @SerialName("price") val price: Double,
    @SerialName("priceSuffix") val priceSuffix: String,
    @SerialName("currency") val currency: String = "AZN",
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("ratingCount") val ratingCount: Int = 0,
    @SerialName("category") val category: String? = null
) {
    fun toDomain(): WishlistItem = WishlistItem(
        wishlistItemId = wishlistItemId,
        serviceType = serviceType,
        serviceId = serviceId,
        title = title,
        image = image,
        price = price,
        priceSuffix = priceSuffix,
        currency = currency,
        rating = rating,
        ratingCount = ratingCount,
        category = category
    )
}

@Serializable
data class WishlistResponseDto(
    @SerialName("items") val items: List<WishlistItemDto> = emptyList(),
    @SerialName("hasMore") val hasMore: Boolean = false,
    @SerialName("nextCursor") val nextCursor: String? = null
) {
    fun toDomain(): WishlistPage = WishlistPage(
        items = items.map { it.toDomain() },
        hasMore = hasMore,
        nextCursor = nextCursor
    )
}
