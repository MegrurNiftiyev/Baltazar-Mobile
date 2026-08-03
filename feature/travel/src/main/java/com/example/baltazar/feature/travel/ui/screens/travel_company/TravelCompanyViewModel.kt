package com.example.baltazar.feature.travel.ui.screens.travel_company

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TravelCompanyViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TravelCompanyState())
    val state: StateFlow<TravelCompanyState> = _state.asStateFlow()
}
