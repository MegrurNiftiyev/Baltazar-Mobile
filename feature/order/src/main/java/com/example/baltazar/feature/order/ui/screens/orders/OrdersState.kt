package com.example.baltazar.feature.order.ui.screens.orders

import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.domain.model.User
import com.example.baltazar.feature.order.domain.model.Order

data class OrdersState(
    val user: User = SessionManager.DEFAULT_GUEST_USER,
    val isUserLoading: Boolean = true,
    val isLoading: Boolean = true,
    val orders: List<Order> = emptyList(),
    val errorMessage: String? = null
)
