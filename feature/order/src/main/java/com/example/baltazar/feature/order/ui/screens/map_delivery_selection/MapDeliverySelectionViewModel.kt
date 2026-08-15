package com.example.baltazar.feature.order.ui.screens.map_delivery_selection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapDeliverySelectionViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MapDeliverySelectionState())
    val state: StateFlow<MapDeliverySelectionState> = _state.asStateFlow()
}
