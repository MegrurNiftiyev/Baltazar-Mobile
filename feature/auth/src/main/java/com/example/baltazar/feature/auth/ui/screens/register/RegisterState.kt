package com.example.baltazar.feature.auth.ui.screens.register

import com.example.baltazar.feature.auth.core.error.ValidationError

data class RegisterState(
    val isLoading: Boolean = false,
    val nameError: ValidationError? = null,
    val emailError: ValidationError? = null,
    val phoneError: ValidationError? = null,
    val passwordError: ValidationError? = null,
    val generalError: String? = null
)
