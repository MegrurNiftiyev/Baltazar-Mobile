package com.example.baltazar.feature.explore.ui.screens.explore

import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.domain.model.User
import com.example.baltazar.feature.explore.domain.model.BannerItem
import com.example.baltazar.feature.explore.domain.model.ExploreSection

data class ExploreState(
    val isBannersLoading: Boolean = false,
    val isSectionsLoading: Boolean = false,
    val isBannersError: Boolean = false,
    val isSectionsError: Boolean = false,
    val banners: List<BannerItem> = emptyList(),
    val sections: List<ExploreSection> = emptyList(),
    val user: User = SessionManager.DEFAULT_GUEST_USER,
    val userMessage: SnackbarMessage? = null
)
