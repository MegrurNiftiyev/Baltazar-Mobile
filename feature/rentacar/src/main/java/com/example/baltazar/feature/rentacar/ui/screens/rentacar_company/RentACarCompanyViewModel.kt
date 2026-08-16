package com.example.baltazar.feature.rentacar.ui.screens.rentacar_company

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RentACarCompanyViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(RentACarCompanyState())
    val state: StateFlow<RentACarCompanyState> = _state.asStateFlow()
}
