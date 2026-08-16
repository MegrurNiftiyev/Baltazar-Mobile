package com.example.baltazar.core.core.components.cards.internal

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ItemAdditionalInfo(
    info: String?,
    modifier: Modifier = Modifier
) {
    if (!info.isNullOrBlank()) {
        Text(
            text = info,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
        )
    }
}
