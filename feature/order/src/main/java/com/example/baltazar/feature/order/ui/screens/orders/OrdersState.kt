package com.example.baltazar.feature.order.ui.screens.orders

data class OrdersState(
    val isLoading: Boolean = false,
    val orders: List<Any> = emptyList(),
    val errorMessage: String? = null
)
