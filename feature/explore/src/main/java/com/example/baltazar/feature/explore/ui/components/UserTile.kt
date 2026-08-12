package com.example.baltazar.feature.explore.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CircularImage
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun UserTile(
    userName: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    isGuest: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            )
            .padding(vertical = Paddings.Small, horizontal = Paddings.Medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage(
            imageUrl = imageUrl,
            size = 48.dp,
            borderWidth = 1.5.dp,
            borderColor = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(Spaces.Medium))
        Column {
            Text(
                text = userName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            if (isGuest) {
                Text(
                    text = stringResource(R.string.sidebar_guest_user),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
