package com.example.baltazar.feature.travel.ui.screens.travel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TravelState())
    val state: StateFlow<TravelState> = _state.asStateFlow()
}
