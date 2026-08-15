package com.example.baltazar.feature.travel.ui.screens.travel_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.core.domain.repository.IIncludedServiceRepository
import com.example.baltazar.core.domain.repository.IReviewRepository
import com.example.baltazar.core.domain.repository.IWishlistRepository
import com.example.baltazar.feature.travel.domain.repository.ITravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelDetailViewModel @Inject constructor(
    private val travelRepository: ITravelRepository,
    private val reviewRepository: IReviewRepository,
    private val wishlistRepository: IWishlistRepository,
    private val includedServiceRepository: IIncludedServiceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tourId: String = savedStateHandle["id"] ?: savedStateHandle["tourId"] ?: ""
    private val _state = MutableStateFlow(TravelDetailState())
    val state: StateFlow<TravelDetailState> = _state.asStateFlow()

    init {
        loadTourDetails()
        loadReviews()
        loadIncludedServices()
    }

    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun loadTourDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            travelRepository.getTourDetail(tourId)
                .onSuccess { detail ->
                    _state.update { it.copy(tour = detail, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun loadIncludedServices() {
        viewModelScope.launch(Dispatchers.IO) {
            includedServiceRepository.getIncludedServices("TRAVEL")
                .onSuccess { services ->
                    _state.update { it.copy(includedServices = services) }
                }
        }
    }

    private fun loadReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isReviewsLoading = true) }
            reviewRepository.getReviews(targetType = "TRAVEL", targetId = tourId)
                .onSuccess { paginatedList ->
                    _state.update { it.copy(reviews = paginatedList.items, isReviewsLoading = false) }
                }
                .onFailure {
                    _state.update { it.copy(isReviewsLoading = false) }
                }
        }
    }

    fun toggleFavorite(isFav: Boolean) {
        _state.update { it.copy(isFavorite = isFav) }
        viewModelScope.launch(Dispatchers.IO) {
            if (isFav) {
                wishlistRepository.addToWishlist(serviceId = tourId, serviceType = ServiceType.TRAVEL)
            } else {
                wishlistRepository.removeFromWishlist(id = tourId)
            }
        }
    }

    fun submitReview(rating: Int, comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSubmittingReview = true) }
            reviewRepository.createReview(
                targetType = "TRAVEL",
                targetId = tourId,
                rating = rating,
                comment = comment
            ).onSuccess {
                _state.update {
                    it.copy(
                        isSubmittingReview = false,
                        userMessage = SnackbarMessage(
                            text = UiText.DynamicString("Rəyiniz uğurla əlavə olundu"),
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
}
