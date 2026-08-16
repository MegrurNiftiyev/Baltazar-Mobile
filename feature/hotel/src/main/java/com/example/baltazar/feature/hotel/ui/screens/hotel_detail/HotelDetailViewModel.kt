package com.example.baltazar.feature.hotel.ui.screens.hotel_detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HotelDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HotelDetailState())
    val state: StateFlow<HotelDetailState> = _state.asStateFlow()
}
