package com.example.baltazar.core.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowLeft
import compose.icons.tablericons.Heart
import compose.icons.tablericons.Share

@Composable
fun DetailTopBarOverlay(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavoriteClick: ((Boolean) -> Unit)? = null,
    onShareClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = Paddings.Medium, vertical = Paddings.Small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DetailFloatingActionButton(
            icon = TablerIcons.ArrowLeft,
            contentDescription = "Back",
            onClick = onBackClick
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spaces.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onShareClick != null) {
                DetailFloatingActionButton(
                    icon = TablerIcons.Share,
                    contentDescription = "Share",
                    onClick = onShareClick
                )
            }

            if (onFavoriteClick != null) {
                DetailFloatingActionButton(
                    icon = if (isFavorite) Icons.Filled.Favorite else TablerIcons.Heart,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                    onClick = { onFavoriteClick(!isFavorite) }
                )
            }
        }
    }
}
