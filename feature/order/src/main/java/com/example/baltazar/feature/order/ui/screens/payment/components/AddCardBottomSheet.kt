package com.example.baltazar.feature.order.ui.screens.payment.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomTextField
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardBottomSheet(
    cardNumberInput: String,
    cardHolderInput: String,
    expiryMonthInput: String,
    expiryYearInput: String,
    cvvInput: String,
    cardNumberError: String?,
    cardHolderError: String?,
    expiryMonthError: String?,
    expiryYearError: String?,
    cvvError: String?,
    addCardError: String?,
    isTokenizingCard: Boolean,
    onDismiss: () -> Unit,
    onCardNumberChange: (String) -> Unit,
    onCardHolderChange: (String) -> Unit,
    onExpiryMonthChange: (String) -> Unit,
    onExpiryYearChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        modifier = modifier
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
                value = cardNumberInput,
                onValueChange = onCardNumberChange,
                label = stringResource(id = R.string.card_number),
                keyboardType = KeyboardType.Number,
                errorText = cardNumberError
            )
            Spacer(modifier = Modifier.height(Spaces.Small))

            CustomTextField(
                value = cardHolderInput,
                onValueChange = onCardHolderChange,
                label = stringResource(id = R.string.card_holder_name),
                keyboardType = KeyboardType.Text,
                errorText = cardHolderError
            )
            Spacer(modifier = Modifier.height(Spaces.Small))

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    CustomTextField(
                        value = expiryMonthInput,
                        onValueChange = onExpiryMonthChange,
                        label = stringResource(id = R.string.expiry_month),
                        keyboardType = KeyboardType.Number,
                        errorText = expiryMonthError
                    )
                }
                Spacer(modifier = Modifier.width(Spaces.Small))
                Box(modifier = Modifier.weight(1f)) {
                    CustomTextField(
                        value = expiryYearInput,
                        onValueChange = onExpiryYearChange,
                        label = stringResource(id = R.string.expiry_year),
                        keyboardType = KeyboardType.Number,
                        errorText = expiryYearError
                    )
                }
                Spacer(modifier = Modifier.width(Spaces.Small))
                Box(modifier = Modifier.weight(1f)) {
                    CustomTextField(
                        value = cvvInput,
                        onValueChange = onCvvChange,
                        label = stringResource(id = R.string.cvv),
                        keyboardType = KeyboardType.Number,
                        errorText = cvvError
                    )
                }
            }

            addCardError?.let { err ->
                Spacer(modifier = Modifier.height(Spaces.Small))
                Text(
                    text = err,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(Spaces.Medium))

            Button(
                onClick = onSubmit,
                enabled = !isTokenizingCard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Paddings.Massive)
            ) {
                if (isTokenizingCard) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(IconSizes.Small),
                        color = Color.White
                    )
                } else {
                    Text(text = stringResource(id = R.string.save_card))
                }
            }
            Spacer(modifier = Modifier.height(Spaces.Large))
        }
    }
}
