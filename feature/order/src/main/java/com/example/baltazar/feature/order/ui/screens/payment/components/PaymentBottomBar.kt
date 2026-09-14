package com.example.baltazar.feature.order.ui.screens.payment.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings

@Composable
fun PaymentBottomBar(
    isProcessingPayment: Boolean,
    isCardSelected: Boolean,
    formattedPrice: String,
    onPayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(Paddings.Medium)
    ) {
        Button(
            onClick = onPayClick,
            enabled = !isProcessingPayment && isCardSelected,
            shape = RoundedCornerShape(BorderRadiuses.Huge),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(Paddings.ColossalMinus)
        ) {
            if (isProcessingPayment) {
                CircularProgressIndicator(
                    modifier = Modifier.size(IconSizes.Medium),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = BorderRadiuses.ExtraMini
                )
            } else {
                Text(
                    text = stringResource(id = R.string.pay_now_format, formattedPrice),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
