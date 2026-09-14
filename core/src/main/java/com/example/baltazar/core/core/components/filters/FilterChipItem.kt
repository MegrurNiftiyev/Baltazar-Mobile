package com.example.baltazar.core.core.components.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronDown
import compose.icons.tablericons.X

@Composable
fun FilterChipItem(
    label: String,
    selectedValue: String? = null,
    isActive: Boolean = selectedValue != null,
    onClick: () -> Unit,
    onClearClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val displayTitle = if (selectedValue.isNullOrBlank()) label else "$label: $selectedValue"
    val containerColor = if (isActive) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceContainerLow
    }
    val contentColor = if (isActive) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val borderColor = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(BorderRadiuses.Large))
            .background(containerColor)
            .border(1.dp, borderColor, RoundedCornerShape(BorderRadiuses.Large))
            .clickable { onClick() }
            .padding(horizontal = Paddings.Medium, vertical = Paddings.SmallMinus)
    ) {
        Text(
            text = displayTitle,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
            color = contentColor
        )

        Spacer(modifier = Modifier.width(Spaces.ExtraMini))

        if (isActive && onClearClick != null) {
            Icon(
                imageVector = TablerIcons.X,
                contentDescription = "Clear",
                tint = contentColor,
                modifier = Modifier
                    .size(IconSizes.MediumMinus)
                    .clickable { onClearClick() }
            )
        } else {
            Icon(
                imageVector = TablerIcons.ChevronDown,
                contentDescription = null,
                tint = contentColor.copy(alpha = 0.7f),
                modifier = Modifier.size(IconSizes.MediumMinus)
            )
        }
    }
}
