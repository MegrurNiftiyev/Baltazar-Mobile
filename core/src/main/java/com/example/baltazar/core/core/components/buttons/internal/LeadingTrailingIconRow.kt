package com.example.baltazar.core.core.components.internal

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Composable
internal fun LeadingTrailingIconRow(
    text: String,
    textStyle: TextStyle,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?,
    leadingIconSize: Dp,
    trailingIconSize: Dp,
    leadingIconTint: Color,
    trailingIconTint: Color,
    leadingIconSpacing: Dp,
    trailingIconSpacing: Dp,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        leadingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(leadingIconSize),
                tint = leadingIconTint
            )
            Spacer(modifier = Modifier.width(leadingIconSpacing))
        }

        Text(text = text, style = textStyle)

        trailingIcon?.let {
            Spacer(modifier = Modifier.width(trailingIconSpacing))
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(trailingIconSize),
                tint = trailingIconTint
            )
        }
    }
}
