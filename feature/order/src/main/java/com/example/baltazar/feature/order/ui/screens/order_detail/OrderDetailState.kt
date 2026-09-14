package com.example.baltazar.feature.order.ui.screens.order_detail

import com.example.baltazar.feature.order.domain.model.Order

data class OrderDetailState(
    val isLoading: Boolean = true,
    val isCancelling: Boolean = false,
    val order: Order? = null,
    val errorMessage: String? = null,
    val isCancelledSuccess: Boolean = false
)
