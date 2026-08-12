package com.example.baltazar.feature.auth.ui.screens.register

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.UiText

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val nameError: UiText? = null,
    val emailError: UiText? = null,
    val phoneError: UiText? = null,
    val passwordError: UiText? = null,
    val generalError: UiText? = null,
    val userMessage: SnackbarMessage? = null
)
