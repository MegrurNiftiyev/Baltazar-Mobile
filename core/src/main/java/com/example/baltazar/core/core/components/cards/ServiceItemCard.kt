package com.example.baltazar.core.core.components

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.enums.CardViewMode

@Composable
fun ServiceItemCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    image: String? = null,
    title: String? = null,
    subtitle: String? = null,
    price: Double? = null,
    currency: String? = "AZN",
    priceSuffix: String? = null,
    rating: Double? = null
) {
    StandardItemCard(
        onClick = onClick,
        modifier = modifier.width(220.dp),
        cardViewMode = CardViewMode.GRID,
        isLoading = isLoading,
        imageUrl = image,
        title = title,
        subtitle = subtitle,
        price = price,
        currency = currency,
        priceSuffix = priceSuffix,
        rating = rating
    )
}
