package com.example.baltazar.core.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.SnackbarType
import com.example.baltazar.core.core.theme.OnWarningDark
import com.example.baltazar.core.core.theme.OnWarningLight
import com.example.baltazar.core.core.theme.WarningDark
import com.example.baltazar.core.core.theme.WarningLight
import com.example.baltazar.core.core.utils.AppSnackbarVisuals
import compose.icons.TablerIcons
import compose.icons.tablericons.AlertCircle
import compose.icons.tablericons.AlertTriangle
import compose.icons.tablericons.CircleCheck

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier.padding(Paddings.Medium)
    ) { data ->
        val customVisuals = data.visuals as? AppSnackbarVisuals
        val type = customVisuals?.type ?: SnackbarType.Success

        val isDark = MaterialTheme.colorScheme.background.red < 0.5f

        val (backgroundColor, contentColor, icon) = when (type) {
            SnackbarType.Success -> Triple(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.onPrimary,
                TablerIcons.CircleCheck
            )
            SnackbarType.Error -> Triple(
                MaterialTheme.colorScheme.error,
                MaterialTheme.colorScheme.onError,
                TablerIcons.AlertCircle
            )
            SnackbarType.Warning -> Triple(
                if (isDark) WarningDark else WarningLight,
                if (isDark) OnWarningDark else OnWarningLight,
                TablerIcons.AlertTriangle
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(BorderRadiuses.Medium),
            color = backgroundColor,
            contentColor = contentColor,
            tonalElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = Paddings.Medium, vertical = Paddings.Small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(Spaces.Small))
                Text(
                    text = data.visuals.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor
                )
            }
        }
    }
}
