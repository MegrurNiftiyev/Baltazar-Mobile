package com.example.baltazar.feature.rentacar.ui.screens.rentacars

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RentACarsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(RentACarsState())
    val state: StateFlow<RentACarsState> = _state.asStateFlow()
}
