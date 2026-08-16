package com.example.baltazar.feature.order.ui.screens.order_summary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OrderSummaryViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(OrderSummaryState())
    val state: StateFlow<OrderSummaryState> = _state.asStateFlow()
}
