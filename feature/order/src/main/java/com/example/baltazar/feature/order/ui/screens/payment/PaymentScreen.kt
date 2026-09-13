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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavHostController,
    onPaymentSuccessNavigate: () -> Unit = {},
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val totalPrice = state.order?.totalPrice ?: 0.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.payment_method),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
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
                        CircularProgressIndicator(
                            modifier = Modifier.size(IconSizes.Medium),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = BorderRadiuses.ExtraMini
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(IconSizes.Small)
                            )
                            Spacer(modifier = Modifier.width(Spaces.Small))
                            Text(
                                text = stringResource(id = R.string.pay_now_format, "\$$totalPrice"),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
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
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
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
                                text = "\$$totalPrice",
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
                                text = "\$$totalPrice",
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

                OutlinedTextField(
                    value = state.cardNumberInput,
                    onValueChange = { viewModel.updateCardNumber(it) },
                    label = { Text(text = stringResource(id = R.string.card_number)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spaces.Small))

                OutlinedTextField(
                    value = state.cardHolderInput,
                    onValueChange = { viewModel.updateCardHolder(it) },
                    label = { Text(text = stringResource(id = R.string.card_holder_name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spaces.Small))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.expiryMonthInput,
                        onValueChange = { viewModel.updateExpiryMonth(it) },
                        label = { Text(text = stringResource(id = R.string.expiry_month)) },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(Spaces.Small))
                    OutlinedTextField(
                        value = state.expiryYearInput,
                        onValueChange = { viewModel.updateExpiryYear(it) },
                        label = { Text(text = stringResource(id = R.string.expiry_year)) },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(Spaces.Small))
                    OutlinedTextField(
                        value = state.cvvInput,
                        onValueChange = { viewModel.updateCvv(it) },
                        label = { Text(text = stringResource(id = R.string.cvv)) },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
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
