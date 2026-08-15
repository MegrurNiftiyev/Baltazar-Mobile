package com.example.baltazar.feature.rentacar.ui.screens.car_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.core.domain.repository.IReviewRepository
import com.example.baltazar.core.domain.repository.IWishlistRepository
import com.example.baltazar.feature.rentacar.domain.repository.IRentACarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val rentACarRepository: IRentACarRepository,
    private val reviewRepository: IReviewRepository,
    private val wishlistRepository: IWishlistRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val carId: String = savedStateHandle["id"] ?: savedStateHandle["carId"] ?: ""
    private val _state = MutableStateFlow(CarDetailState())
    val state: StateFlow<CarDetailState> = _state.asStateFlow()

    init {
        loadCarDetails()
        loadReviews()
    }

    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun loadCarDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            rentACarRepository.getCarDetail(carId)
                .onSuccess { detail ->
                    _state.update { it.copy(car = detail, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun loadReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isReviewsLoading = true) }
            reviewRepository.getReviews(targetType = "RENT_A_CAR", targetId = carId)
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
                wishlistRepository.addToWishlist(serviceId = carId, serviceType = ServiceType.RENT_A_CAR)
            } else {
                wishlistRepository.removeFromWishlist(id = carId)
            }
        }
    }

    fun submitReview(rating: Int, comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSubmittingReview = true) }
            reviewRepository.createReview(
                targetType = "RENT_A_CAR",
                targetId = carId,
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
