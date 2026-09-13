package com.example.baltazar.feature.order.ui.screens.payment

import com.example.baltazar.feature.order.domain.model.Order
import com.example.baltazar.feature.order.domain.model.PaymentCard

data class PaymentState(
    val isLoading: Boolean = false,
    val order: Order? = null,
    val savedCards: List<PaymentCard> = emptyList(),
    val selectedCardId: String? = null,
    val isAddCardSheetOpen: Boolean = false,
    val isTokenizingCard: Boolean = false,
    val cardNumberInput: String = "",
    val cardHolderInput: String = "",
    val expiryMonthInput: String = "",
    val expiryYearInput: String = "",
    val cvvInput: String = "",
    val addCardError: String? = null,
    val isProcessingPayment: Boolean = false,
    val isPaymentDeclined: Boolean = false,
    val paymentErrorMessage: String? = null
)
