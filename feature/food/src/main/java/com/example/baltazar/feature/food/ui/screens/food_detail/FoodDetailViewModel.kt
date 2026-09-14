package com.example.baltazar.feature.food.ui.screens.food_detail

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
import com.example.baltazar.feature.food.domain.repository.IFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.baltazar.core.core.managers.AuthGateManager
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val foodRepository: IFoodRepository,
    private val reviewRepository: IReviewRepository,
    private val wishlistRepository: IWishlistRepository,
    val authGateManager: AuthGateManager,
    private val sessionManager: SessionManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val foodId: String = savedStateHandle["id"] ?: ""
    private val _state = MutableStateFlow(FoodDetailState())
    val state: StateFlow<FoodDetailState> = _state.asStateFlow()

    init {
        loadFoodDetails()
        loadReviews()
    }

    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun loadFoodDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            foodRepository.getFoodDetail(foodId)
                .onSuccess { detail ->
                    _state.update { it.copy(food = detail, isFavorite = detail.isLiked, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun loadReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isReviewsLoading = true) }
            reviewRepository.getReviews(targetType = ServiceType.FOOD.name, targetId = foodId)
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
            kotlinx.coroutines.withContext(kotlinx.coroutines.NonCancellable) {
                if (isFav) {
                    wishlistRepository.addToWishlist(serviceId = foodId, serviceType = ServiceType.FOOD)
                } else {
                    wishlistRepository.removeFromWishlist(id = foodId)
                }
            }
        }
    }

    fun incrementQuantity() = _state.update { it.copy(quantity = it.quantity + 1) }
    fun decrementQuantity() = _state.update { it.copy(quantity = (it.quantity - 1).coerceAtLeast(1)) }

    fun submitReview(rating: Int, comment: String) {
        if (sessionManager.user.value.isGuest) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSubmittingReview = true) }
            reviewRepository.createReview(
                targetType = ServiceType.FOOD.name,
                targetId = foodId,
                rating = rating,
                comment = comment
            ).onSuccess {
                _state.update { state ->
                    val updatedEligibility = state.food.reviewEligibility.copy(canSubmit = false, alreadyReviewed = true)
                    val updatedFood = state.food.copy(reviewEligibility = updatedEligibility)
                    state.copy(
                        food = updatedFood,
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
