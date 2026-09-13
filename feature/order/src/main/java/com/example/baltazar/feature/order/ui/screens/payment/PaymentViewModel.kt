package com.example.baltazar.feature.order.ui.screens.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import com.example.baltazar.feature.order.domain.repository.IPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    private val paymentRepository: IPaymentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderId: String = savedStateHandle["orderId"] ?: ""

    private val _state = MutableStateFlow(PaymentState())
    val state: StateFlow<PaymentState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        if (orderId.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, paymentErrorMessage = null) }
            
            // 1. Fetch Order details
            orderRepository.getOrderDetails(orderId).onSuccess { order ->
                _state.update { it.copy(order = order) }
            }

            // 2. Fetch saved cards from Baltazar backend
            paymentRepository.getAllCards()
                .onSuccess { cards ->
                    // Sort cards newest first (or by ID/date)
                    val sortedCards = cards.reversed()
                    val defaultSelectedId = sortedCards.firstOrNull()?.paymentMethodId
                    _state.update {
                        it.copy(
                            isLoading = false,
                            savedCards = sortedCards,
                            selectedCardId = defaultSelectedId
                        )
                    }
                }
                .onFailure {
                    _state.update { state -> state.copy(isLoading = false) }
                }
        }
    }

    fun selectCard(paymentMethodId: String) {
        _state.update { it.copy(selectedCardId = paymentMethodId) }
    }

    fun openAddCardSheet() {
        _state.update {
            it.copy(
                isAddCardSheetOpen = true,
                cardNumberInput = "",
                cardHolderInput = "",
                expiryMonthInput = "",
                expiryYearInput = "",
                cvvInput = "",
                addCardError = null
            )
        }
    }

    fun closeAddCardSheet() {
        _state.update { it.copy(isAddCardSheetOpen = false) }
    }

    fun updateCardNumber(value: String) { _state.update { it.copy(cardNumberInput = value) } }
    fun updateCardHolder(value: String) { _state.update { it.copy(cardHolderInput = value) } }
    fun updateExpiryMonth(value: String) { _state.update { it.copy(expiryMonthInput = value) } }
    fun updateExpiryYear(value: String) { _state.update { it.copy(expiryYearInput = value) } }
    fun updateCvv(value: String) { _state.update { it.copy(cvvInput = value) } }

    fun submitNewCard() {
        val s = _state.value
        if (s.cardNumberInput.isBlank() || s.cardHolderInput.isBlank() || s.cvvInput.isBlank()) {
            _state.update { it.copy(addCardError = "Please fill in all card details") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isTokenizingCard = true, addCardError = null) }

            // Step A: Tokenize card via External Payment Gateway API
            paymentRepository.tokenizeCard(
                cardNumber = s.cardNumberInput.replace(" ", ""),
                cardHolder = s.cardHolderInput,
                expiryMonth = s.expiryMonthInput,
                expiryYear = s.expiryYearInput,
                cvv = s.cvvInput
            ).onSuccess { tokenized ->
                // Step B: Save tokenized card to Baltazar Backend API
                paymentRepository.addPaymentMethod(
                    paymentMethodId = tokenized.paymentMethodId,
                    brand = tokenized.brand,
                    last4 = tokenized.last4,
                    expiryMonth = tokenized.expiryMonth,
                    expiryYear = tokenized.expiryYear
                ).onSuccess { newCard ->
                    _state.update { state ->
                        val updatedList = listOf(newCard) + state.savedCards
                        state.copy(
                            isTokenizingCard = false,
                            isAddCardSheetOpen = false,
                            savedCards = updatedList,
                            selectedCardId = newCard.paymentMethodId
                        )
                    }
                }.onFailure { error ->
                    _state.update { state ->
                        state.copy(isTokenizingCard = false, addCardError = error.message ?: "Failed to save card")
                    }
                }
            }.onFailure { error ->
                _state.update { state ->
                    state.copy(isTokenizingCard = false, addCardError = error.message ?: "Card tokenization failed")
                }
            }
        }
    }

    fun processPayment(onPaymentSuccess: () -> Unit) {
        val selectedCardId = _state.value.selectedCardId ?: return
        if (orderId.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isProcessingPayment = true, isPaymentDeclined = false, paymentErrorMessage = null) }

            // 1. Attach payment method to order
            orderRepository.patchPaymentMethod(orderId, selectedCardId).onSuccess {
                // 2. Process payment
                paymentRepository.processPayment(orderId, selectedCardId)
                    .onSuccess { txn ->
                        _state.update { it.copy(isProcessingPayment = false) }
                        if (txn.status == "SUCCESS") {
                            onPaymentSuccess()
                        } else {
                            _state.update { it.copy(isPaymentDeclined = true) }
                        }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                isProcessingPayment = false,
                                isPaymentDeclined = true,
                                paymentErrorMessage = error.message ?: "Payment declined"
                            )
                        }
                    }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isProcessingPayment = false,
                        isPaymentDeclined = true,
                        paymentErrorMessage = error.message
                    )
                }
            }
        }
    }

    fun dismissPaymentError() {
        _state.update { it.copy(isPaymentDeclined = false) }
    }
}
