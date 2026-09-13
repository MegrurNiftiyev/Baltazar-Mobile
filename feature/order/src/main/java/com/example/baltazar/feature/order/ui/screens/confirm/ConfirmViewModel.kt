package com.example.baltazar.feature.order.ui.screens.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderId: String = savedStateHandle["orderId"] ?: ""

    private val _state = MutableStateFlow(ConfirmState())
    val state: StateFlow<ConfirmState> = _state.asStateFlow()

    init {
        loadOrderDetails()
    }

    fun loadOrderDetails() {
        if (orderId.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            orderRepository.getOrderDetails(orderId)
                .onSuccess { order ->
                    _state.update { it.copy(isLoading = false, order = order) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }
}
