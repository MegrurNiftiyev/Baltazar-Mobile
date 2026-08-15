package com.example.baltazar.feature.order.ui.screens.orders

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrdersViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OrdersState())
    val uiState: StateFlow<OrdersState> = _uiState.asStateFlow()
}
