package com.example.baltazar.feature.travel.ui.screens.travel_detail

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.domain.model.IncludedService
import com.example.baltazar.core.domain.model.ReviewItem
import com.example.baltazar.feature.travel.domain.model.TourDetail

data class TravelDetailState(
    val tour: TourDetail = TourDetail(),
    val includedServices: List<IncludedService> = emptyList(),
    val reviews: List<ReviewItem> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val isReviewsLoading: Boolean = false,
    val isSubmittingReview: Boolean = false,
    val userMessage: SnackbarMessage? = null,
    val error: String? = null
)
