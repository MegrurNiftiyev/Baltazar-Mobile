package com.example.baltazar.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.constants.Spaces

@Composable
fun TextWithAction(
    text: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    spacing: androidx.compose.ui.unit.Dp = Spaces.Tiny,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    actionTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    actionColor: Color = MaterialTheme.colorScheme.primary,
    actionFontWeight: FontWeight = FontWeight.SemiBold
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
        Text(
            text = actionText,
            style = actionTextStyle,
            color = actionColor,
            fontWeight = actionFontWeight,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onActionClick
            )
        )
    }
}