package com.example.baltazar.feature.hotel.ui.screens.hotel_detail

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.domain.model.ReviewItem
import com.example.baltazar.feature.hotel.domain.model.HotelDetail
import com.example.baltazar.feature.hotel.domain.model.HotelRoom

data class HotelDetailState(
    val hotel: HotelDetail = HotelDetail(),
    val rooms: List<HotelRoom> = emptyList(),
    val selectedRoomId: String? = null,
    val reviews: List<ReviewItem> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val isRoomsLoading: Boolean = false,
    val isReviewsLoading: Boolean = false,
    val isSubmittingReview: Boolean = false,
    val userMessage: SnackbarMessage? = null,
    val error: String? = null
) {
    val effectivePrice: Double
        get() = rooms.find { it.id == selectedRoomId }?.price ?: hotel.minPrice
}
