package com.example.baltazar.feature.food.ui.screens.foods

import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.feature.food.domain.model.FoodItem

data class FoodsState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val items: List<FoodItem> = emptyList(),
    val nextCursor: String? = null,
    val hasMore: Boolean = true,
    val error: String? = null,
    val cardViewMode: CardViewMode = CardViewMode.GRID
)
