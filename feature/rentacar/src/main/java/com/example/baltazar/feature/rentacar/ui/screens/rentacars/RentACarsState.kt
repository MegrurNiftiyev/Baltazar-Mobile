package com.example.baltazar.feature.rentacar.ui.screens.rentacars

import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.feature.rentacar.domain.model.CarItem

data class RentACarsState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val items: List<CarItem> = emptyList(),
    val nextCursor: String? = null,
    val hasMore: Boolean = true,
    val error: String? = null,
    val cardViewMode: CardViewMode = CardViewMode.GRID
)
