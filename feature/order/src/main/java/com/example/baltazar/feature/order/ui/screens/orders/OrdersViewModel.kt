package com.example.baltazar.feature.order.ui.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersState())
    val state: StateFlow<OrdersState> = _state.asStateFlow()

    init {
        observeUserSession()
    }

    private fun observeUserSession() {
        viewModelScope.launch {
            sessionManager.isLoadingUser.collect { isLoading ->
                _state.update { it.copy(isUserLoading = isLoading) }
            }
        }
        viewModelScope.launch {
            sessionManager.user.collect { user ->
                _state.update { it.copy(user = user) }
                if (!user.isGuest) {
                    fetchOrders()
                } else if (!sessionManager.isLoadingUser.value) {
                    _state.update { it.copy(orders = emptyList(), isLoading = false) }
                }
            }
        }
    }

    fun fetchOrders() {
        if (sessionManager.user.value.isGuest) {
            _state.update { it.copy(isLoading = false) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            orderRepository.getOrders()
                .onSuccess { paginated ->
                    _state.update { it.copy(isLoading = false, orders = paginated.items) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }
}
