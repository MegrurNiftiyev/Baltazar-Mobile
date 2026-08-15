package com.example.baltazar.feature.rentacar.ui.screens.car_detail

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.domain.model.ReviewItem
import com.example.baltazar.feature.rentacar.domain.model.CarDetail

data class CarDetailState(
    val car: CarDetail = CarDetail(),
    val reviews: List<ReviewItem> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val isReviewsLoading: Boolean = false,
    val isSubmittingReview: Boolean = false,
    val userMessage: SnackbarMessage? = null,
    val error: String? = null
)
