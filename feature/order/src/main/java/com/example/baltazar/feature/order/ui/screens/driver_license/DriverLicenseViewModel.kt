package com.example.baltazar.feature.order.ui.screens.driver_license

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DriverLicenseViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(DriverLicenseState())
    val state: StateFlow<DriverLicenseState> = _state.asStateFlow()
}
