package com.example.baltazar.core.core.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.wear.compose.material3.TextButton
import androidx.wear.compose.material3.TextButtonDefaults
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
        colors = TextButtonDefaults.textButtonColors(
            contentColor = contentColor,
            disabledContentColor = disabledContentColor
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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
}
