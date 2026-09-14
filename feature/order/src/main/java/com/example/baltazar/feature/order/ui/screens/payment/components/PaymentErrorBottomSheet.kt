package com.example.baltazar.feature.order.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentErrorBottomSheet(
    errorMessage: String?,
    onRetry: () -> Unit,
    onChooseDifferentCard: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier
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
                text = errorMessage ?: stringResource(id = R.string.card_declined_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Spaces.Large))

            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Paddings.Massive)
            ) {
                Text(text = stringResource(id = R.string.try_again))
            }

            Spacer(modifier = Modifier.height(Spaces.Small))

            OutlinedButton(
                onClick = onChooseDifferentCard,
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
