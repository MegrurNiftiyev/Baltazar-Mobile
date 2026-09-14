package com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R

@Composable
fun LocationPermissionDialog(
    visible: Boolean,
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(id = R.string.location_permission_required)) },
            text = { Text(text = stringResource(id = R.string.permission_denied_settings_msg)) },
            confirmButton = {
                TextButton(onClick = onOpenSettings) {
                    Text(text = stringResource(id = R.string.open_settings))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}
