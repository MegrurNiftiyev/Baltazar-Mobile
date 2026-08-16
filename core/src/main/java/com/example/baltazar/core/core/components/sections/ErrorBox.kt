package com.example.baltazar.core.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import compose.icons.TablerIcons
import compose.icons.tablericons.CloudOff

@Composable
fun ErrorBox(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.explore_error_title),
    subtitle: String? = stringResource(R.string.explore_error_subtitle),
    retryText: String = stringResource(R.string.explore_retry),
    icon: ImageVector = TablerIcons.CloudOff,
    onRetry: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(BorderRadiuses.Large),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.Medium, vertical = Paddings.Small)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.Large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(Spaces.Medium))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            if (!subtitle.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(Spaces.Mini))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
            if (onRetry != null) {
                Spacer(modifier = Modifier.height(Spaces.Medium))
                RoundedButton(
                    text = retryText,
                    onClick = onRetry,
                    modifier = Modifier.width(180.dp)
                )
            }
        }
    }
}
