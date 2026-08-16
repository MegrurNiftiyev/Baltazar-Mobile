package com.example.baltazar.core.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.core.components.internal.LeadingTrailingIconRow
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape
import com.example.baltazar.core.core.extensions.toShape

@Composable
fun RoundedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    content: (@Composable RowScope.() -> Unit)? = null,
    shape: CornerShape = CornerShape.Circle,
    borderRadius: Dp = BorderRadiuses.Medium,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContainerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(
        defaultElevation = Spaces.Mini,
        pressedElevation = Spaces.Tiny
    ),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,

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
    require((text != null) xor (content != null)) {
        "RoundedButton: provide either 'text' or 'content', not both, not neither."
    }

    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(Spaces.ColossalPlus),
        shape = shape.toShape(borderRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        elevation = elevation
    ) {
        Box(contentAlignment = Alignment.Center) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(IconSizes.Medium),
                        color = contentColor,
                        strokeWidth = Paddings.ExtraMini
                    )
                }

                content != null -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        content()
                    }
                }

                else -> {
                    LeadingTrailingIconRow(
                        text = text!!,
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
        }
    }
}
