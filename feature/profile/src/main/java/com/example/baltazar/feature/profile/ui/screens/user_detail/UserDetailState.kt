package com.example.baltazar.feature.profile.ui.screens.user_detail

import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.UiText

data class UserDetailState(
    val name: String = "",
    val phone: String = "",
    val nameError: UiText? = null,
    val phoneError: UiText? = null,
    val selectedRegion: Region = Region.AZ,
    val selectedLanguage: Language = Language.AZ,
    val isLanguageSheetOpen: Boolean = false,
    val isRegionSheetOpen: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val userMessage: SnackbarMessage? = null
)
