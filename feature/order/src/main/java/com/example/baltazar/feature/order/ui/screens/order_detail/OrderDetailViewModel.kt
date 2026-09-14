package com.example.baltazar.feature.order.ui.screens.order_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderId: String = savedStateHandle["orderId"] ?: ""

    private val _state = MutableStateFlow(OrderDetailState())
    val state: StateFlow<OrderDetailState> = _state.asStateFlow()

    init {
        loadOrderDetails()
    }

    fun loadOrderDetails() {
        if (orderId.isBlank()) {
            _state.update { it.copy(isLoading = false, errorMessage = "Invalid order ID") }
            return
        }

        viewModelScope.launch(IO) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            orderRepository.getOrderDetails(orderId)
                .onSuccess { order ->
                    _state.update { it.copy(isLoading = false, order = order) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }

    fun cancelOrder() {
        if (orderId.isBlank()) return
        viewModelScope.launch(IO) {
            _state.update { it.copy(isCancelling = true) }
            orderRepository.cancelOrder(orderId)
                .onSuccess { updatedOrder ->
                    _state.update {
                        it.copy(
                            isCancelling = false,
                            order = updatedOrder,
                            isCancelledSuccess = true
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isCancelling = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    fun continueOrderFlow(onResolvedNextScreen: (NextScreenType, String) -> Unit) {
        if (orderId.isBlank()) return
        viewModelScope.launch(IO) {
            _state.update { it.copy(isLoading = true) }
            orderRepository.getNextScreen(orderId)
                .onSuccess { nextResult ->
                    _state.update { it.copy(isLoading = false) }
                    withContext(Dispatchers.Main) {
                        onResolvedNextScreen(nextResult.screen, orderId)
                    }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }
}
