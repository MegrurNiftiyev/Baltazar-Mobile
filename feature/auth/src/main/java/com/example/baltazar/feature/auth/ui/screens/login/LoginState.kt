package com.example.baltazar.feature.auth.ui.screens.login

import com.example.baltazar.feature.auth.core.error.ValidationError

data class LoginState(
    val isLoading: Boolean = false,
    val emailError: ValidationError? = null,
    val passwordError: ValidationError? = null,
    val generalError: String? = null
)
