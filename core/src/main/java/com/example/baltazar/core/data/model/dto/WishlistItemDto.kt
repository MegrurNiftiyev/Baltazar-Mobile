package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.domain.model.ServiceCardItem
import com.example.baltazar.core.domain.model.WishlistItem
import com.example.baltazar.core.domain.model.WishlistPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceRangeDto(
    @SerialName("min") val min: Double? = null,
    @SerialName("max") val max: Double? = null
)

@Serializable
data class WishlistItemDto(
    @SerialName("wishlistItemId") val wishlistItemId: String? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("serviceType") val serviceType: ServiceType = ServiceType.FOOD,
    @SerialName("serviceId") val serviceId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("brand") val brand: String? = null,
    @SerialName("model") val model: String? = null,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("image") val image: String? = null,
    @SerialName("price") val price: Double = 0.0,
    @SerialName("priceRange") val priceRange: PriceRangeDto? = null,
    @SerialName("priceSuffix") val priceSuffix: String? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("reviewCount") val reviewCount: Int = 0,
    @SerialName("ratingCount") val ratingCount: Int = 0,
    @SerialName("category") val category: String? = null,
    @SerialName("categories") val categories: List<String> = emptyList(),
    @SerialName("isLiked") val isLiked: Boolean = true
) {
    fun toDomain(): ServiceCardItem {
        val effectiveId = wishlistItemId ?: id ?: serviceId ?: ""
        val effectiveServiceId = serviceId ?: id ?: wishlistItemId ?: ""
        val effectiveTitle = when {
            !title.isNullOrBlank() -> title
            !name.isNullOrBlank() -> name
            !brand.isNullOrBlank() || !model.isNullOrBlank() -> listOfNotNull(brand, model).joinToString(" ")
            else -> ""
        }
        val effectiveImage = image ?: images.firstOrNull() ?: ""
        val effectiveRatingCount = if (reviewCount > 0) reviewCount else ratingCount
        val effectiveCategory = category ?: categories.firstOrNull() ?: ""
        val effectivePrice = if (price > 0.0) price else (priceRange?.min ?: 0.0)
        val effectiveSuffix = priceSuffix.orEmpty()

        return ServiceCardItem(
            id = effectiveId,
            serviceType = serviceType,
            serviceId = effectiveServiceId,
            title = effectiveTitle,
            image = effectiveImage,
            price = effectivePrice,
            priceSuffix = effectiveSuffix,
            currency = currency.orEmpty(),
            rating = rating,
            ratingCount = effectiveRatingCount,
            category = effectiveCategory,
            isLiked = isLiked
        )
    }

    fun toWishlistItemDomain(): WishlistItem {
        val domainItem = toDomain()
        return WishlistItem(
            wishlistItemId = domainItem.id,
            serviceType = domainItem.serviceType,
            serviceId = domainItem.serviceId,
            title = domainItem.title,
            image = domainItem.image,
            price = domainItem.price,
            priceSuffix = domainItem.priceSuffix,
            currency = domainItem.currency,
            rating = domainItem.rating,
            ratingCount = domainItem.ratingCount,
            category = domainItem.category ?: "",
            isLiked = domainItem.isLiked
        )
    }
}

@Serializable
data class WishlistResponseDto(
    @SerialName("items") val items: List<WishlistItemDto> = emptyList(),
    @SerialName("hasMore") val hasMore: Boolean = false,
    @SerialName("nextCursor") val nextCursor: String? = null
) {
    fun toDomain(): WishlistPage = WishlistPage(
        items = items.map { it.toWishlistItemDomain() },
        hasMore = hasMore,
        nextCursor = nextCursor
    )
}
