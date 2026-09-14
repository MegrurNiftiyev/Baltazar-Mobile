package com.example.baltazar.feature.profile.ui.screens.driver_license

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.UiText

data class DriverLicenseState(
    val licenseNumber: String = "",
    val expiryDate: String = "",
    val licenseNumberError: UiText? = null,
    val expiryDateError: UiText? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val userMessage: SnackbarMessage? = null
)
