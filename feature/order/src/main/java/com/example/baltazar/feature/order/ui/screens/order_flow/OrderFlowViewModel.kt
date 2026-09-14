package com.example.baltazar.feature.order.ui.screens.order_flow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.navigation.OrderFlow
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
class OrderFlowViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val routeData: OrderFlow? = try { savedStateHandle.toRoute<OrderFlow>() } catch (_: Exception) { null }
    private val serviceTypeStr: String = routeData?.serviceType ?: savedStateHandle.get<String>("serviceType") ?: ""
    private val serviceId: String = routeData?.serviceId ?: savedStateHandle.get<String>("serviceId") ?: ""
    private val subItemId: String? = savedStateHandle.get<String>("subItemId")

    private var isOrderFlowInitiated = false

    private val _state = MutableStateFlow(OrderFlowState())
    val state: StateFlow<OrderFlowState> = _state.asStateFlow()

    fun initOrderFlow(onResolvedNextScreen: (NextScreenType, String) -> Unit) {
        if (isOrderFlowInitiated) return
        if (serviceTypeStr.isBlank() || serviceId.isBlank()) {
            _state.update { it.copy(isLoading = false, errorMessage = "Invalid order request details") }
            return
        }
        val serviceType = try {
            ServiceType.valueOf(serviceTypeStr.uppercase())
        } catch (_: Exception) {
            _state.update { it.copy(isLoading = false, errorMessage = "Invalid service type") }
            return
        }

        isOrderFlowInitiated = true

        viewModelScope.launch(IO) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            orderRepository.createOrder(serviceType, serviceId, subItemId)
                .onSuccess { order ->
                    _state.update { it.copy(order = order) }
                    // Resolve next screen dynamically
                    orderRepository.getNextScreen(order.id)
                        .onSuccess { nextScreenResult ->
                            _state.update { state -> state.copy(isLoading = false, nextScreenType = nextScreenResult.screen) }
                            withContext(Dispatchers.Main) {
                                onResolvedNextScreen(nextScreenResult.screen, order.id)
                            }
                        }
                        .onFailure { error ->
                            _state.update { state -> state.copy(isLoading = false, errorMessage = error.message ?: "Failed to resolve next screen") }
                        }
                }
                .onFailure { error ->
                    _state.update { state -> state.copy(isLoading = false, errorMessage = error.message ?: "Failed to create order") }
                }
        }
    }
}
