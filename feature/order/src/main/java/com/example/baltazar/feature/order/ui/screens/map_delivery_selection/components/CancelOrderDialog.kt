package com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAlertDialog

@Composable
fun CancelOrderDialog(
    visible: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (visible) {
        CustomAlertDialog(
            title = stringResource(id = R.string.cancel_order_dialog_title),
            subtitle = stringResource(id = R.string.cancel_order_dialog_msg),
            confirmText = stringResource(id = R.string.yes_cancel),
            cancelText = stringResource(id = R.string.no_stay),
            isDestructive = true,
            onConfirm = onConfirm,
            onCancel = onDismiss
        )
    }
}
