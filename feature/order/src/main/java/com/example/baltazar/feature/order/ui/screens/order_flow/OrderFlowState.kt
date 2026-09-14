package com.example.baltazar.feature.order.ui.screens.order_flow

import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.domain.model.Order

data class OrderFlowState(
    val isLoading: Boolean = false,
    val order: Order? = null,
    val nextScreenType: NextScreenType? = null,
    val errorMessage: String? = null
)
