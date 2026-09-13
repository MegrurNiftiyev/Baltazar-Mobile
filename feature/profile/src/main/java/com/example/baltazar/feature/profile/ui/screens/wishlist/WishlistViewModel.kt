package com.example.baltazar.feature.profile.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.domain.model.ServiceCardItem
import com.example.baltazar.core.domain.repository.IWishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: IWishlistRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(WishlistState())
    val state: StateFlow<WishlistState> = _state.asStateFlow()

    init {
        observeUserSession()
    }

    private fun observeUserSession() {
        viewModelScope.launch {
            sessionManager.user.collect { user ->
                val isLoggedIn = !user.isGuest
                _state.update { it.copy(user = user) }
                if (isLoggedIn) {
                    loadWishlist()
                } else {
                    _state.update { it.copy(items = emptyList(), isLoading = false) }
                }
            }
        }
        viewModelScope.launch {
            sessionManager.isLoadingUser.collect { isLoading ->
                _state.update { it.copy(isUserLoading = isLoading) }
            }
        }
    }

    fun loadWishlist() {
        val currentUser = sessionManager.user.value
        if (currentUser.isGuest) {
            _state.update { it.copy(isLoading = false, isRefreshing = false) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            wishlistRepository.getWishlist()
                .onSuccess { wishlistItems ->
                    _state.update {
                        it.copy(
                            items = wishlistItems,
                            isLoading = false,
                            isRefreshing = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = exception.message ?: "Failed to load wishlist"
                        )
                    }
                }
        }
    }

    fun refreshWishlist() {
        val currentUser = _state.value.user
        if (currentUser.isGuest) {
            _state.update { it.copy(isRefreshing = false) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, error = null) }
            wishlistRepository.getWishlist()
                .onSuccess { wishlistItems ->
                    _state.update {
                        it.copy(
                            items = wishlistItems,
                            isRefreshing = false,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            isLoading = false,
                            error = exception.message ?: "Failed to refresh wishlist"
                        )
                    }
                }
        }
    }

    fun toggleCardViewMode() {
        _state.update {
            it.copy(
                cardViewMode = if (it.cardViewMode == CardViewMode.GRID) CardViewMode.LIST else CardViewMode.GRID
            )
        }
    }

    fun toggleFavoriteById(itemId: String, isFav: Boolean) {
        if (!isFav) {
            _state.update { state ->
                state.copy(items = state.items.filter { it.id != itemId })
            }
        }
    }

    fun toggleFavorite(item: ServiceCardItem, isFav: Boolean) {
        viewModelScope.launch {
            kotlinx.coroutines.withContext(kotlinx.coroutines.NonCancellable) {
                if (!isFav) {
                    // Optimistically remove from list
                    _state.update { state ->
                        state.copy(items = state.items.filter { it.id != item.id })
                    }
                    val result = wishlistRepository.removeFromWishlist(item.id)
                    if (result.isFailure) {
                        // Revert if API call fails
                        _state.update { state ->
                            state.copy(items = state.items + item)
                        }
                    }
                } else {
                    wishlistRepository.addToWishlist(item.id, item.serviceType)
                }
            }
        }
    }
}
