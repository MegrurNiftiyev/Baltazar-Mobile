package com.example.baltazar.feature.auth.ui.screens.login

import com.example.baltazar.core.core.utils.UiText

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val generalError: UiText? = null
)

