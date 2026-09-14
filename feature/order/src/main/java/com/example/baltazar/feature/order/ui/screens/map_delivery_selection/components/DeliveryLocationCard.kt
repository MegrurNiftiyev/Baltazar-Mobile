package com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun DeliveryLocationCard(
    addressName: String,
    deliveryInstructions: String,
    isSaving: Boolean,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(topStart = BorderRadiuses.ExtraLarge, topEnd = BorderRadiuses.ExtraLarge),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = Spaces.Large,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(Paddings.Medium)
        ) {
            Box(
                modifier = Modifier
                    .width(Spaces.Massive)
                    .height(BorderRadiuses.ExtraMini)
                    .clip(RoundedCornerShape(BorderRadiuses.Small))
                    .background(MaterialTheme.colorScheme.outlineVariant)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(Spaces.Medium))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(Paddings.Max)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(IconSizes.Medium)
                    )
                }

                Spacer(modifier = Modifier.width(Spaces.Medium))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = addressName.ifBlank { stringResource(id = R.string.delivery_address) },
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (deliveryInstructions.isNotBlank()) {
                        Spacer(modifier = Modifier.height(Spaces.ExtraSmall))
                        Text(
                            text = deliveryInstructions,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spaces.Large))

            Button(
                onClick = onConfirm,
                enabled = !isSaving && addressName.isNotBlank(),
                shape = RoundedCornerShape(BorderRadiuses.Huge),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Paddings.ColossalMinus)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(IconSizes.Medium),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = BorderRadiuses.ExtraMini
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.confirm_address),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
