package com.example.baltazar.feature.taxi.ui.screens.taxi

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TaxiViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TaxiState())
    val state: StateFlow<TaxiState> = _state.asStateFlow()
}
