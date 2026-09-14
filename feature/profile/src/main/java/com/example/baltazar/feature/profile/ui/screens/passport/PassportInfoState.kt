package com.example.baltazar.feature.profile.ui.screens.passport

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.UiText

data class PassportInfoState(
    val passportNumber: String = "",
    val expiryDate: String = "",
    val passportNumberError: UiText? = null,
    val expiryDateError: UiText? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val userMessage: SnackbarMessage? = null
)
