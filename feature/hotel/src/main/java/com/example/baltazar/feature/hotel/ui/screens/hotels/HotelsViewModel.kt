package com.example.baltazar.feature.hotel.ui.screens.hotels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HotelsState())
    val state: StateFlow<HotelsState> = _state.asStateFlow()
}
