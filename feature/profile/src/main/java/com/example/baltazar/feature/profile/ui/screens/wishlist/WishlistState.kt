package com.example.baltazar.feature.profile.ui.screens.wishlist

import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.domain.model.ServiceCardItem
import com.example.baltazar.core.domain.model.User

data class WishlistState(
    val user: User = SessionManager.DEFAULT_GUEST_USER,
    val isUserLoading: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val items: List<ServiceCardItem> = emptyList(),
    val error: String? = null,
    val cardViewMode: CardViewMode = CardViewMode.GRID
)

