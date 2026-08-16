package com.example.baltazar.feature.profile.ui.screens.profile

import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.domain.model.User

data class ProfileState(
    val user: User = SessionManager.DEFAULT_GUEST_USER,
    val isDarkMode: Boolean = false,
    val selectedLanguage: Language = Language.AZ,
    val selectedRegion: Region = Region.AZ,
    val cardViewMode: CardViewMode = CardViewMode.GRID,
    val isLanguageSheetOpen: Boolean = false,
    val isRegionSheetOpen: Boolean = false,
    val isCardStyleSheetOpen: Boolean = false,
    val isLogoutDialogOpen: Boolean = false,
    val isLoggedOut: Boolean = false
)
