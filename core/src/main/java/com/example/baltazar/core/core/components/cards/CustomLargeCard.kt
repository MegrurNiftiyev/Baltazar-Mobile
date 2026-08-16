package com.example.baltazar.core.core.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.core.constants.IconSizes

@Composable
fun CustomLargeCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    CustomCard(
        label = title,
        value = subtitle,
        modifier = modifier,
        icon = icon,
        onClick = onClick,
        trailingContent = trailingContent,
        iconSize = IconSizes.Large,
        labelStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        valueStyle = MaterialTheme.typography.bodySmall
    )
}
