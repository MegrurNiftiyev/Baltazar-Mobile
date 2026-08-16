package com.example.baltazar.core.core.components.cards.internal

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.core.extensions.formatPrice

@Composable
fun ItemPriceText(
    price: Double?,
    currency: String?,
    priceSuffix: String?,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (price != null) formatPrice(price, currency.orEmpty(), priceSuffix.orEmpty()) else "",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        modifier = modifier
    )
}
