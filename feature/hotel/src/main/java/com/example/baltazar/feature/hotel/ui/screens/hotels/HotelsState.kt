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
    val cardViewMode: CardViewMode = CardViewMode.GRID
)
