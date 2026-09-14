package com.example.baltazar.feature.profile.ui.screens.user_detail

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.UiText

data class UserDetailState(
    val name: String = "",
    val phone: String = "",
    val nameError: UiText? = null,
    val phoneError: UiText? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val userMessage: SnackbarMessage? = null
)
