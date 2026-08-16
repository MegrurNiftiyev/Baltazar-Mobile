package com.example.baltazar.feature.order.ui.screens.delivery_address

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DeliveryAddressViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(DeliveryAddressState())
    val state: StateFlow<DeliveryAddressState> = _state.asStateFlow()
}
