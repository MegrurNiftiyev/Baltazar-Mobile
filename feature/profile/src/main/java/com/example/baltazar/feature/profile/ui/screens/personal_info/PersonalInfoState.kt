package com.example.baltazar.feature.profile.ui.screens.personal_info

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.UiText

data class PersonalInfoState(
    val dateOfBirth: String = "",
    val address: String = "",
    val idNumber: String = "",
    val dateOfBirthError: UiText? = null,
    val addressError: UiText? = null,
    val idNumberError: UiText? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val userMessage: SnackbarMessage? = null
)
