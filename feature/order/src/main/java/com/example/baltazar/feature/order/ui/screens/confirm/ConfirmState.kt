package com.example.baltazar.feature.order.ui.screens.confirm

import com.example.baltazar.feature.order.domain.model.Order

data class ConfirmState(
    val isLoading: Boolean = false,
    val order: Order? = null,
    val errorMessage: String? = null
)
