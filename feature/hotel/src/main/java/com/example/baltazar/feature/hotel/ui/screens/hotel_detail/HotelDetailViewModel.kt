package com.example.baltazar.feature.hotel.ui.screens.hotel_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.R
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.core.domain.repository.IReviewRepository
import com.example.baltazar.core.domain.repository.IWishlistRepository
import com.example.baltazar.feature.hotel.domain.repository.IHotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.baltazar.core.core.managers.AuthGateManager
import com.example.baltazar.core.core.managers.SessionManager

@HiltViewModel
class HotelDetailViewModel @Inject constructor(
    private val hotelRepository: IHotelRepository,
    private val reviewRepository: IReviewRepository,
    private val wishlistRepository: IWishlistRepository,
    val authGateManager: AuthGateManager,
    private val sessionManager: SessionManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val hotelId: String = savedStateHandle["id"] ?: ""
    private val _state = MutableStateFlow(HotelDetailState())
    val state: StateFlow<HotelDetailState> = _state.asStateFlow()

    init {
        loadHotelDetails()
        loadHotelRooms()
        loadReviews()
    }

    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun selectRoom(roomId: String) {
        _state.update { it.copy(selectedRoomId = roomId) }
    }

    fun loadHotelDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            hotelRepository.getHotelDetail(hotelId)
                .onSuccess { detail ->
                    _state.update { it.copy(hotel = detail, isFavorite = detail.isLiked, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun loadHotelRooms() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRoomsLoading = true) }
            hotelRepository.getHotelRooms(hotelId)
                .onSuccess { roomsList ->
                    val lowestPricedRoom = roomsList.minByOrNull { it.price }
                    _state.update {
                        it.copy(
                            rooms = roomsList,
                            isRoomsLoading = false,
                            selectedRoomId = it.selectedRoomId ?: lowestPricedRoom?.id
                        )
                    }
                }
                .onFailure {
                    _state.update { it.copy(isRoomsLoading = false) }
                }
        }
    }

    private fun loadReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isReviewsLoading = true) }
            reviewRepository.getReviews(targetType = ServiceType.HOTEL.name, targetId = hotelId)
                .onSuccess { paginatedList ->
                    _state.update { it.copy(reviews = paginatedList.items, isReviewsLoading = false) }
                }
                .onFailure {
                    _state.update { it.copy(isReviewsLoading = false) }
                }
        }
    }

    fun toggleFavorite(isFav: Boolean) {
        if (sessionManager.user.value.isGuest) {
            return
        }

        _state.update { it.copy(isFavorite = isFav) }
        viewModelScope.launch(Dispatchers.IO) {
            withContext(NonCancellable) {
                if (isFav) {
                    wishlistRepository.addToWishlist(serviceId = hotelId, serviceType = ServiceType.HOTEL)
                } else {
                    wishlistRepository.removeFromWishlist(id = hotelId)
                }
            }
        }
    }

    fun submitReview(rating: Int, comment: String) {
        if (sessionManager.user.value.isGuest) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSubmittingReview = true) }
            reviewRepository.createReview(
                targetType = ServiceType.HOTEL.name,
                targetId = hotelId,
                rating = rating,
                comment = comment
            ).onSuccess {
                _state.update { state ->
                    val updatedEligibility = state.hotel.reviewEligibility.copy(canSubmit = false, alreadyReviewed = true)
                    val updatedHotel = state.hotel.copy(reviewEligibility = updatedEligibility)
                    state.copy(
                        hotel = updatedHotel,
                        isSubmittingReview = false,
                        userMessage = SnackbarMessage(
                            text = UiText.StringResource(R.string.review_submitted_success),
                            type = SnackbarType.SUCCESS
                        )
                    )
                }
                loadReviews()
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isSubmittingReview = false,
                        userMessage = SnackbarMessage(
                            text = UiText.DynamicString(error.message ?: "Xəta baş verdi"),
                            type = SnackbarType.ERROR
                        )
                    )
                }
            }
        }
    }

    fun isGuest(): Boolean = authGateManager.isGuest()
}
