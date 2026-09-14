package com.example.baltazar.core.core.components.cards.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Spaces
import compose.icons.TablerIcons
import compose.icons.tablericons.Heart

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = IconSizes.Large,
    iconSize: Dp = IconSizes.Small
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.35f))
            .clickable(onClick = onFavoriteClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = TablerIcons.Heart,
            contentDescription = "Favorite",
            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surface,
            modifier = Modifier.size(iconSize)
        )
    }
}
