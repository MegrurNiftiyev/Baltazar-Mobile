package com.example.baltazar.feature.order.ui.screens.order_flow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderFlowViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val serviceTypeStr: String = savedStateHandle["serviceType"] ?: ""
    private val serviceId: String = savedStateHandle["serviceId"] ?: ""
    private val subItemId: String? = savedStateHandle["subItemId"]

    private val _state = MutableStateFlow(OrderFlowState())
    val state: StateFlow<OrderFlowState> = _state.asStateFlow()

    fun initOrderFlow(onResolvedNextScreen: (NextScreenType, String) -> Unit) {
        if (serviceTypeStr.isBlank() || serviceId.isBlank()) return
        val serviceType = try {
            ServiceType.valueOf(serviceTypeStr.uppercase())
        } catch (_: Exception) {
            _state.update { it.copy(errorMessage = "Invalid service type") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            orderRepository.createOrder(serviceType, serviceId, subItemId)
                .onSuccess { order ->
                    _state.update { it.copy(order = order) }
                    // Resolve next screen dynamically
                    orderRepository.getNextScreen(order.id)
                        .onSuccess { nextScreenResult ->
                            _state.update { state -> state.copy(isLoading = false, nextScreenType = nextScreenResult.screen) }
                            onResolvedNextScreen(nextScreenResult.screen, order.id)
                        }
                        .onFailure { error ->
                            _state.update { state -> state.copy(isLoading = false, errorMessage = error.message) }
                        }
                }
                .onFailure { error ->
                    _state.update { state -> state.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }
}
