package com.example.baltazar.feature.order.ui.screens.order_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.feature.order.domain.model.Order
import com.example.baltazar.feature.order.domain.model.OrderStatus

@Composable
fun OrderActions(
    order: Order,
    isCancelling: Boolean,
    onContinue: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPending = order.status == OrderStatus.PENDING || order.status == OrderStatus.AWAITING_PAYMENT || order.status == OrderStatus.PROCESSING
    val isCancellable = isPending || order.status != OrderStatus.CANCELLED

    Column(modifier = modifier.fillMaxWidth()) {
        if (isPending) {
            Button(
                onClick = onContinue,
                shape = RoundedCornerShape(BorderRadiuses.Huge),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Paddings.ColossalMinus)
            ) {
                Text(
                    text = stringResource(R.string.continue_order),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        if (isPending && isCancellable && order.status != OrderStatus.CANCELLED) {
            Spacer(modifier = Modifier.height(Spaces.Small))
        }

        if (isCancellable && order.status != OrderStatus.CANCELLED) {
            OutlinedButton(
                onClick = onCancel,
                enabled = !isCancelling,
                shape = RoundedCornerShape(BorderRadiuses.Huge),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Paddings.ColossalMinus)
            ) {
                if (isCancelling) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(IconSizes.Medium),
                        color = MaterialTheme.colorScheme.error,
                        strokeWidth = BorderRadiuses.ExtraMini
                    )
                } else {
                    Text(
                        text = stringResource(R.string.cancel_order),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
