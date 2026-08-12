package com.example.baltazar.core.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun RoundedIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    inactiveIcon: ImageVector? = null,
    isActive: Boolean = true,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    borderRadius: Dp? = null,
    elevation: Dp = 0.dp,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    inactiveContainerColor: Color = containerColor,
    inactiveContentColor: Color = contentColor,
    disabledContainerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    size: Dp = Spaces.Giant,
    iconSize: Dp = IconSizes.Medium,
    contentDescription: String? = null
) {
    val shape: Shape = if (borderRadius != null) {
        RoundedCornerShape(borderRadius)
    } else {
        CircleShape
    }

    val currentContainerColor = when {
        !enabled -> disabledContainerColor
        isActive -> containerColor
        else -> inactiveContainerColor
    }

    val currentContentColor = when {
        !enabled -> disabledContentColor
        isActive -> contentColor
        else -> inactiveContentColor
    }

    val currentIcon = if (!isActive && inactiveIcon != null) {
        inactiveIcon
    } else {
        icon
    }

    val borderStroke = if (borderWidth > 0.dp && borderColor != Color.Unspecified) {
        BorderStroke(borderWidth, borderColor)
    } else {
        null
    }

    Surface(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier.size(size),
        shape = shape,
        color = currentContainerColor,
        contentColor = currentContentColor,
        shadowElevation = elevation,
        border = borderStroke
    ) {
        Box(
            modifier = Modifier.padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(iconSize),
                    color = currentContentColor,
                    strokeWidth = Paddings.ExtraMini
                )
            } else {
                Icon(
                    imageVector = currentIcon,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}
