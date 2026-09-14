package com.example.baltazar.feature.order.ui.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.CustomTextField
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.TitleAlignment

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.baltazar.core.core.components.CustomAlertDialog
import com.example.baltazar.core.core.navigation.Home

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.Medium)
            ) {
                Button(
                    onClick = { viewModel.processPayment(onPaymentSuccessNavigate) },
                    enabled = !state.isProcessingPayment && state.selectedCardId != null,
                    shape = RoundedCornerShape(BorderRadiuses.Huge),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.ColossalMinus)
                ) {
                    if (state.isProcessingPayment) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(IconSizes.Medium),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = BorderRadiuses.ExtraMini
                            )
                            Spacer(modifier = Modifier.width(Spaces.Small))
                            Text(
                                text = stringResource(id = R.string.redirecting_to_next_screen),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } else {
                        Text(
                            text = stringResource(id = R.string.pay_now_format, formattedPrice),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(Spaces.Small))
                    Text(
                        text = stringResource(id = R.string.redirecting_to_next_screen),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = Paddings.Medium)
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.saved_cards),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = Paddings.Small)
                    )
                }

                // Saved Cards List
                items(state.savedCards) { card ->
                    val isSelected = state.selectedCardId == card.paymentMethodId
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Paddings.ExtraSmall)
                            .clip(RoundedCornerShape(BorderRadiuses.Medium))
                            .border(
                                width = if (isSelected) BorderRadiuses.ExtraMini else BorderRadiuses.ExtraMini / 2,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(BorderRadiuses.Medium)
                            )
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                                else MaterialTheme.colorScheme.surface
                            )
                            .clickable { viewModel.selectCard(card.paymentMethodId) }
                            .padding(Paddings.Medium)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(IconSizes.Giant)
                                .clip(RoundedCornerShape(BorderRadiuses.Small))
                                .background(MaterialTheme.colorScheme.surfaceContainerLow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CreditCard,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.width(Spaces.Medium))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "•••• ${card.last4}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${card.expiryMonth}/${card.expiryYear}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        RadioButton(
                            selected = isSelected,
                            onClick = { viewModel.selectCard(card.paymentMethodId) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(Spaces.Small))

                    // Dashed Add New Card Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(BorderRadiuses.Medium))
                            .border(
                                width = BorderRadiuses.ExtraMini / 2,
                                color = MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(BorderRadiuses.Medium)
                            )
                            .clickable { viewModel.openAddCardSheet() }
                            .padding(Paddings.Medium),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(Spaces.Small))
                            Text(
                                text = stringResource(id = R.string.add_new_card),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(Spaces.Large))

                    // Order Summary Card
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(BorderRadiuses.Large))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .padding(Paddings.Medium)
                    ) {
                        Text(
                            text = stringResource(id = R.string.order_summary),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(Spaces.Medium))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.base_price),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = formattedPrice,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(Spaces.Small))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Spacer(modifier = Modifier.height(Spaces.Small))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.total),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = formattedPrice,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Card BottomSheet
    if (state.isAddCardSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeAddCardSheet() },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.Medium)
            ) {
                Text(
                    text = stringResource(id = R.string.add_new_card),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spaces.Medium))

                CustomTextField(
                    value = state.cardNumberInput,
                    onValueChange = { viewModel.updateCardNumber(it) },
                    label = stringResource(id = R.string.card_number),
                    keyboardType = KeyboardType.Number,
                    errorText = state.cardNumberError
                )
                Spacer(modifier = Modifier.height(Spaces.Small))

                CustomTextField(
                    value = state.cardHolderInput,
                    onValueChange = { viewModel.updateCardHolder(it) },
                    label = stringResource(id = R.string.card_holder_name),
                    keyboardType = KeyboardType.Text,
                    errorText = state.cardHolderError
                )
                Spacer(modifier = Modifier.height(Spaces.Small))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(
                            value = state.expiryMonthInput,
                            onValueChange = { viewModel.updateExpiryMonth(it) },
                            label = stringResource(id = R.string.expiry_month),
                            keyboardType = KeyboardType.Number,
                            errorText = state.expiryMonthError
                        )
                    }
                    Spacer(modifier = Modifier.width(Spaces.Small))
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(
                            value = state.expiryYearInput,
                            onValueChange = { viewModel.updateExpiryYear(it) },
                            label = stringResource(id = R.string.expiry_year),
                            keyboardType = KeyboardType.Number,
                            errorText = state.expiryYearError
                        )
                    }
                    Spacer(modifier = Modifier.width(Spaces.Small))
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(
                            value = state.cvvInput,
                            onValueChange = { viewModel.updateCvv(it) },
                            label = stringResource(id = R.string.cvv),
                            keyboardType = KeyboardType.Number,
                            errorText = state.cvvError
                        )
                    }
                }

                state.addCardError?.let { err ->
                    Spacer(modifier = Modifier.height(Spaces.Small))
                    Text(text = err, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(Spaces.Medium))

                Button(
                    onClick = { viewModel.submitNewCard() },
                    enabled = !state.isTokenizingCard,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.Massive)
                ) {
                    if (state.isTokenizingCard) {
                        CircularProgressIndicator(modifier = Modifier.size(IconSizes.Small), color = Color.White)
                    } else {
                        Text(text = stringResource(id = R.string.save_card))
                    }
                }
                Spacer(modifier = Modifier.height(Spaces.Large))
            }
        }
    }

    // Payment Error BottomSheet
    if (state.isPaymentDeclined) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.dismissPaymentError() }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.Large)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(IconSizes.Max)
                )
                Spacer(modifier = Modifier.height(Spaces.Medium))
                Text(
                    text = stringResource(id = R.string.card_declined_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spaces.Small))
                Text(
                    text = state.paymentErrorMessage ?: stringResource(id = R.string.card_declined_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spaces.Large))

                Button(
                    onClick = {
                        viewModel.dismissPaymentError()
                        viewModel.processPayment(onPaymentSuccessNavigate)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.Massive)
                ) {
                    Text(text = stringResource(id = R.string.try_again))
                }

                Spacer(modifier = Modifier.height(Spaces.Small))

                OutlinedButton(
                    onClick = { viewModel.dismissPaymentError() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.Massive)
                ) {
                    Text(text = stringResource(id = R.string.choose_different_card))
                }
                Spacer(modifier = Modifier.height(Spaces.Large))
            }
        }
    }
}
