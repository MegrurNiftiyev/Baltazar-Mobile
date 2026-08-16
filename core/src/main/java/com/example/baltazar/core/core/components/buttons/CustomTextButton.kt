package com.example.baltazar.core.core.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.core.components.internal.LeadingTrailingIconRow
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),

    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    iconSize: Dp = IconSizes.Small,
    iconTint: Color = contentColor,
    iconSpacing: Dp = Spaces.Mini,
    leadingIconSize: Dp = iconSize,
    trailingIconSize: Dp = iconSize,
    leadingIconTint: Color = iconTint,
    trailingIconTint: Color = iconTint,
    leadingIconSpacing: Dp = iconSpacing,
    trailingIconSpacing: Dp = iconSpacing,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            disabledContentColor = disabledContentColor
        )
    ) {
        LeadingTrailingIconRow(
            text = text,
            textStyle = textStyle,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            leadingIconSize = leadingIconSize,
            trailingIconSize = trailingIconSize,
            leadingIconTint = leadingIconTint,
            trailingIconTint = trailingIconTint,
            leadingIconSpacing = leadingIconSpacing,
            trailingIconSpacing = trailingIconSpacing
        )
    }
}
