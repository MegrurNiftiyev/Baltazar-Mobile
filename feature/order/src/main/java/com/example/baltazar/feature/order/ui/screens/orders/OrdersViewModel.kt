package com.example.baltazar.feature.order.ui.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
            combine(sessionManager.user, sessionManager.isLoadingUser) { user, isUserLoading ->
                Pair(user, isUserLoading)
            }.collect { (user, isUserLoading) ->
                _state.update { it.copy(user = user, isUserLoading = isUserLoading) }
                if (!isUserLoading) {
                    if (!user.isGuest) {
                        fetchOrders()
                    } else {
                        _state.update { it.copy(orders = emptyList(), isLoading = false) }
                    }
                }
            }
        }
    }

    fun fetchOrders() {
        if (sessionManager.isLoadingUser.value || sessionManager.user.value.isGuest) {
            _state.update { it.copy(isLoading = false) }
            return
        }

        viewModelScope.launch(IO) {
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

    fun cancelOrder(orderId: String) {
        viewModelScope.launch(IO) {
            orderRepository.cancelOrder(orderId)
                .onSuccess {
                    fetchOrders()
                }
                .onFailure { error ->
                    _state.update { it.copy(errorMessage = error.message) }
                }
        }
    }

    fun continueOrderFlow(orderId: String, onResolvedNextScreen: (com.example.baltazar.feature.order.domain.model.NextScreenType, String) -> Unit) {
        viewModelScope.launch(IO) {
            _state.update { it.copy(isLoading = true) }
            orderRepository.getNextScreen(orderId)
                .onSuccess { nextResult ->
                    _state.update { it.copy(isLoading = false) }
                    onResolvedNextScreen(nextResult.screen, orderId)
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }
}
