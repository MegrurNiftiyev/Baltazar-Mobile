package com.example.baltazar.feature.hotel.ui.screens.hotels

import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.feature.hotel.domain.model.HotelItem

data class HotelsState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val items: List<HotelItem> = emptyList(),
    val nextCursor: String? = null,
    val hasMore: Boolean = true,
    val error: String? = null,
    val cardViewMode: CardViewMode = CardViewMode.GRID,

    // Applied filters
    val city: String? = null,
    val starRating: Int? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val includedServices: List<String> = emptyList(),

    // Draft filters
    val draftCity: String? = null,
    val draftStarRating: Int? = null,
    val draftMinPrice: Double? = null,
    val draftMaxPrice: Double? = null,
    val draftIncludedServices: List<String> = emptyList()
) {
    val hasDraftChanges: Boolean
        get() = draftCity != city ||
                draftStarRating != starRating ||
                draftMinPrice != minPrice ||
                draftMaxPrice != maxPrice ||
                draftIncludedServices != includedServices
}

