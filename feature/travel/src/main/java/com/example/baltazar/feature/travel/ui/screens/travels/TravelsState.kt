package com.example.baltazar.feature.travel.ui.screens.travels

import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.feature.travel.domain.model.TourItem

data class TravelsState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val items: List<TourItem> = emptyList(),
    val nextCursor: String? = null,
    val hasMore: Boolean = true,
    val error: String? = null,
    val cardViewMode: CardViewMode = CardViewMode.GRID
)
