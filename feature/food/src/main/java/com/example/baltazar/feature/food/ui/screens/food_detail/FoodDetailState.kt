package com.example.baltazar.feature.food.ui.screens.food_detail

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.domain.model.ReviewItem
import com.example.baltazar.feature.food.domain.model.FoodDetail

data class FoodDetailState(
    val food: FoodDetail = FoodDetail(),
    val reviews: List<ReviewItem> = emptyList(),
    val isFavorite: Boolean = false,
    val quantity: Int = 1,
    val isLoading: Boolean = true,
    val isReviewsLoading: Boolean = false,
    val isSubmittingReview: Boolean = false,
    val userMessage: SnackbarMessage? = null,
    val error: String? = null
)
