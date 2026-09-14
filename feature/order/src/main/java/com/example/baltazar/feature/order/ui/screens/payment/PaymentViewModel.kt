package com.example.baltazar.feature.order.ui.screens.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import com.example.baltazar.feature.order.domain.repository.IPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        viewModelScope.launch(IO) {
            _state.update { it.copy(isLoading = true, paymentErrorMessage = null) }
            
            // 1. Fetch Order details if orderId present
            if (orderId.isNotBlank()) {
                orderRepository.getOrderDetails(orderId).onSuccess { order ->
                    _state.update { it.copy(order = order) }
                }
            }

            // 2. Fetch saved cards from Baltazar backend
            paymentRepository.getAllCards()
                .onSuccess { cards ->
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
                cardNumberError = null,
                cardHolderError = null,
                expiryMonthError = null,
                expiryYearError = null,
                cvvError = null,
                addCardError = null
            )
        }
    }

    fun closeAddCardSheet() {
        _state.update { it.copy(isAddCardSheetOpen = false) }
    }

    fun updateCardNumber(value: String) {
        val digits = value.filter { it.isDigit() }.take(16)
        val formatted = digits.chunked(4).joinToString(" ")
        _state.update { it.copy(cardNumberInput = formatted, cardNumberError = null) }
    }

    fun updateCardHolder(value: String) {
        _state.update { it.copy(cardHolderInput = value, cardHolderError = null) }
    }

    fun updateExpiryMonth(value: String) {
        val digits = value.filter { it.isDigit() }.take(2)
        _state.update { it.copy(expiryMonthInput = digits, expiryMonthError = null) }
    }

    fun updateExpiryYear(value: String) {
        val digits = value.filter { it.isDigit() }.take(2)
        _state.update { it.copy(expiryYearInput = digits, expiryYearError = null) }
    }

    fun updateCvv(value: String) {
        val digits = value.filter { it.isDigit() }.take(4)
        _state.update { it.copy(cvvInput = digits, cvvError = null) }
    }

    fun submitNewCard() {
        val s = _state.value
        val rawCardNumber = s.cardNumberInput.replace(" ", "")
        val cardHolder = s.cardHolderInput.trim()
        val monthInt = s.expiryMonthInput.toIntOrNull()
        val yearInt = s.expiryYearInput.toIntOrNull()
        val cvv = s.cvvInput.trim()

        var hasError = false
        var cardNumberErr: String? = null
        var cardHolderErr: String? = null
        var expiryMonthErr: String? = null
        var expiryYearErr: String? = null
        var cvvErr: String? = null

        if (rawCardNumber.length != 16) {
            cardNumberErr = "Kart nömrəsi 16 rəqəmdən ibarət olmalıdır"
            hasError = true
        }

        if (cardHolder.isBlank()) {
            cardHolderErr = "Kart sahibinin adını daxil edin"
            hasError = true
        }

        if (monthInt == null || monthInt !in 1..12) {
            expiryMonthErr = "Düzgün ay daxil edin (01-12)"
            hasError = true
        }

        if (yearInt == null || s.expiryYearInput.length != 2) {
            expiryYearErr = "Düzgün il daxil edin (YY)"
            hasError = true
        }

        if (cvv.length !in 3..4) {
            cvvErr = "CVV 3 və ya 4 rəqəmdən ibarət olmalıdır"
            hasError = true
        }

        if (hasError) {
            _state.update {
                it.copy(
                    cardNumberError = cardNumberErr,
                    cardHolderError = cardHolderErr,
                    expiryMonthError = expiryMonthErr,
                    expiryYearError = expiryYearErr,
                    cvvError = cvvErr
                )
            }
            return
        }

        viewModelScope.launch(IO) {
            _state.update { it.copy(isTokenizingCard = true, addCardError = null) }

            // Step A: Tokenize card via External Payment Gateway API
            paymentRepository.tokenizeCard(
                cardNumber = rawCardNumber,
                cardHolder = cardHolder,
                expiryMonth = s.expiryMonthInput.padStart(2, '0'),
                expiryYear = "20" + s.expiryYearInput,
                cvv = cvv
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

        viewModelScope.launch(IO) {
            _state.update { it.copy(isProcessingPayment = true, isPaymentDeclined = false, paymentErrorMessage = null) }

            // 1. Attach payment method to order
            orderRepository.patchPaymentMethod(orderId, selectedCardId).onSuccess {
                // 2. Process payment
                paymentRepository.processPayment(orderId, selectedCardId)
                    .onSuccess { txn ->
                        _state.update { it.copy(isProcessingPayment = false) }
                        if (txn.status == "SUCCESS") {
                            withContext(Main) {
                                onPaymentSuccess()
                            }
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
