package com.example.baltazar.feature.order.ui.screens.payment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAlertDialog
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.feature.order.ui.screens.payment.components.AddCardBottomSheet
import com.example.baltazar.feature.order.ui.screens.payment.components.AddCardButton
import com.example.baltazar.feature.order.ui.screens.payment.components.OrderSummaryCard
import com.example.baltazar.feature.order.ui.screens.payment.components.PaymentBottomBar
import com.example.baltazar.feature.order.ui.screens.payment.components.PaymentErrorBottomSheet
import com.example.baltazar.feature.order.ui.screens.payment.components.PaymentLoading
import com.example.baltazar.feature.order.ui.screens.payment.components.SavedCardsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavHostController,
    onPaymentSuccessNavigate: () -> Unit = {},
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showCancelDialog by remember { mutableStateOf(false) }

    BackHandler {
        showCancelDialog = true
    }

    if (showCancelDialog) {
        CustomAlertDialog(
            title = stringResource(id = R.string.cancel_order_dialog_title),
            subtitle = stringResource(id = R.string.cancel_order_dialog_msg),
            confirmText = stringResource(id = R.string.yes_cancel),
            cancelText = stringResource(id = R.string.no_stay),
            isDestructive = true,
            onConfirm = {
                showCancelDialog = false
                navController.navigate(Home()) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onCancel = { showCancelDialog = false }
        )
    }

    val totalPrice = state.order?.totalPrice?.takeIf { it > 0.0 } ?: state.order?.serviceItemSnapshot?.price ?: 0.0
    val currency = state.order?.serviceItemSnapshot?.currency ?: "AZN"
    val formattedPrice = if (totalPrice % 1.0 == 0.0) "${totalPrice.toLong()} $currency" else "$totalPrice $currency"

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.payment_method),
                alignment = TitleAlignment.CENTER,
                onBackClick = { showCancelDialog = true }
            )
        },
        bottomBar = {
            PaymentBottomBar(
                isProcessingPayment = state.isProcessingPayment,
                isCardSelected = state.selectedCardId != null,
                formattedPrice = formattedPrice,
                onPayClick = { viewModel.processPayment(onPaymentSuccessNavigate) }
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            PaymentLoading(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = Paddings.Medium)
            ) {
                item {
                    SavedCardsSection(
                        cards = state.savedCards,
                        selectedCardId = state.selectedCardId,
                        onCardSelected = viewModel::selectCard
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(Spaces.Small))
                    AddCardButton(
                        onClick = viewModel::openAddCardSheet
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(Spaces.Large))
                    OrderSummaryCard(
                        formattedPrice = formattedPrice
                    )
                }
            }
        }
    }

    if (state.isAddCardSheetOpen) {
        AddCardBottomSheet(
            cardNumberInput = state.cardNumberInput,
            cardHolderInput = state.cardHolderInput,
            expiryMonthInput = state.expiryMonthInput,
            expiryYearInput = state.expiryYearInput,
            cvvInput = state.cvvInput,
            cardNumberError = state.cardNumberError,
            cardHolderError = state.cardHolderError,
            expiryMonthError = state.expiryMonthError,
            expiryYearError = state.expiryYearError,
            cvvError = state.cvvError,
            addCardError = state.addCardError,
            isTokenizingCard = state.isTokenizingCard,
            onDismiss = viewModel::closeAddCardSheet,
            onCardNumberChange = viewModel::updateCardNumber,
            onCardHolderChange = viewModel::updateCardHolder,
            onExpiryMonthChange = viewModel::updateExpiryMonth,
            onExpiryYearChange = viewModel::updateExpiryYear,
            onCvvChange = viewModel::updateCvv,
            onSubmit = viewModel::submitNewCard
        )
    }

    if (state.isPaymentDeclined) {
        PaymentErrorBottomSheet(
            errorMessage = state.paymentErrorMessage,
            onRetry = {
                viewModel.dismissPaymentError()
                viewModel.processPayment(onPaymentSuccessNavigate)
            },
            onChooseDifferentCard = viewModel::dismissPaymentError,
            onDismiss = viewModel::dismissPaymentError
        )
    }
}
