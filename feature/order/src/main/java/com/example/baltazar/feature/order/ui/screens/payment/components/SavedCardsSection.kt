package com.example.baltazar.feature.order.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.feature.order.domain.model.PaymentCard

@Composable
fun SavedCardsSection(
    cards: List<PaymentCard>,
    selectedCardId: String?,
    onCardSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.saved_cards),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = Paddings.Small)
        )

        cards.forEach { card ->
            SavedCardItem(
                card = card,
                isSelected = selectedCardId == card.paymentMethodId,
                onSelect = { onCardSelected(card.paymentMethodId) }
            )
        }
    }
}
