package com.example.baltazar.feature.company.ui.screens.company_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.enums.ReviewTargetType
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.domain.repository.IReviewRepository
import com.example.baltazar.core.domain.repository.IWishlistRepository
import com.example.baltazar.feature.company.domain.repository.ICompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.baltazar.core.R
import com.example.baltazar.core.core.managers.AuthGateManager
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.core.domain.model.ReviewEligibility

@HiltViewModel
class CompanyDetailViewModel @Inject constructor(
    private val companyRepository: ICompanyRepository,
    private val reviewRepository: IReviewRepository,
    private val wishlistRepository: IWishlistRepository,
    val authGateManager: AuthGateManager,
    private val sessionManager: SessionManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var companyId: String = savedStateHandle["id"] ?: ""
    private val _state = MutableStateFlow(CompanyDetailState())
    val state: StateFlow<CompanyDetailState> = _state.asStateFlow()

    init {
        if (companyId.isNotBlank()) {
            loadCompanyDetails()
            loadRelatedItems()
            loadReviews()
        }
    }

    fun setCompanyId(id: String) {
        if (id.isNotBlank() && companyId != id) {
            companyId = id
            loadCompanyDetails()
            loadRelatedItems()
            loadReviews()
        }
    }

    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun loadCompanyDetails() {
        if (companyId.isBlank()) {
            _state.update { it.copy(error = "Məlumat tapılmadı") }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            companyRepository.getCompanyDetails(companyId)
                .onSuccess { detail ->
                    _state.update { it.copy(company = detail, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message ?: "Xəta baş verdi") }
                }
        }
    }

    private fun loadRelatedItems() {
        if (companyId.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRelatedItemsLoading = true) }
            companyRepository.getRelatedItems(companyId)
                .onSuccess { items ->
                    _state.update { it.copy(relatedItems = items, isRelatedItemsLoading = false) }
                }
                .onFailure {
                    _state.update { it.copy(isRelatedItemsLoading = false) }
                }
        }
    }

    private fun loadReviews() {
        if (companyId.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isReviewsLoading = true) }
            reviewRepository.getReviews(targetType = ReviewTargetType.COMPANY.name, targetId = companyId)
                .onSuccess { paginatedList ->
                    _state.update { it.copy(reviews = paginatedList.items, isReviewsLoading = false) }
                }
                .onFailure {
                    _state.update { it.copy(isReviewsLoading = false) }
                }
        }
    }

    fun isGuest(): Boolean = sessionManager.user.value.isGuest

    fun toggleFavorite(isFav: Boolean) {
        if (sessionManager.user.value.isGuest) {
            return
        }

        _state.update { it.copy(isFavorite = isFav) }
        viewModelScope.launch(Dispatchers.IO) {
            withContext(NonCancellable) {
                if (isFav) {
                    wishlistRepository.addToWishlist(serviceId = companyId, serviceType = ServiceType.FOOD)
                } else {
                    wishlistRepository.removeFromWishlist(id = companyId)
                }
            }
        }
    }

    fun toggleRelatedItemFavorite(itemId: String, isFav: Boolean) {
        if (sessionManager.user.value.isGuest) {
            return
        }

        _state.update { currentState ->
            val updated = currentState.relatedItems.map { item ->
                if (item.id == itemId) item.copy(isLiked = isFav) else item
            }
            currentState.copy(relatedItems = updated)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val target = _state.value.relatedItems.firstOrNull { it.id == itemId }
            val serviceType = target?.serviceType ?: ServiceType.FOOD
            withContext(NonCancellable) {
                if (isFav) {
                    wishlistRepository.addToWishlist(serviceId = itemId, serviceType = serviceType)
                } else {
                    wishlistRepository.removeFromWishlist(id = itemId)
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
                targetType = ReviewTargetType.COMPANY.name,
                targetId = companyId,
                rating = rating,
                comment = comment
            ).onSuccess {
                _state.update { state ->
                    val currentCompany = state.company
                    val updatedEligibility = currentCompany?.reviewEligibility?.copy(canSubmit = false, alreadyReviewed = true)
                        ?: ReviewEligibility(canSubmit = false, alreadyReviewed = true)
                    val updatedCompany = currentCompany?.copy(reviewEligibility = updatedEligibility)
                    state.copy(
                        company = updatedCompany,
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
}

