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
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.components.UserAvatar.UserAvatar
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.domain.model.User

@Composable
fun UserTile(
    user: User,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val isGuest = user.role == "GUEST"

    ShimmerWrapper(
        isLoading = isLoading,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null && !isLoading) Modifier.clickable { onClick() } else Modifier
            )
            .padding(vertical = Paddings.Small, horizontal = Paddings.Medium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                user = user,
                size = 48.dp
            )
            Spacer(modifier = Modifier.width(Spaces.Medium))
            Column {
                Text(
                    text = if (isGuest) stringResource(R.string.guest_user) else user.name,
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
                } else if (user.email.isNotBlank()) {
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
