package com.example.baltazar.feature.order.ui.screens.payment

import com.example.baltazar.core.core.utils.UiText
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

    val cardNumberError: UiText? = null,
    val cardHolderError: UiText? = null,
    val expiryMonthError: UiText? = null,
    val expiryYearError: UiText? = null,
    val cvvError: UiText? = null,

    val addCardError: UiText? = null,
    val isProcessingPayment: Boolean = false,
    val isPaymentDeclined: Boolean = false,
    val paymentErrorMessage: String? = null
)
