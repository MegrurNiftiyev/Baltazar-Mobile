package com.example.baltazar.feature.explore.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedIconButton
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.feature.explore.domain.model.QuickActionItem
import compose.icons.TablerIcons
import compose.icons.tablericons.Bed
import compose.icons.tablericons.Car
import compose.icons.tablericons.Plane
import compose.icons.tablericons.ShoppingCart

@Composable
fun ExploreServiceQuickActions(
    onServiceClick: (ServiceType) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    val items = listOf(
        QuickActionItem(R.string.service_hotel, TablerIcons.Bed, ServiceType.HOTEL),
        QuickActionItem(R.string.service_travel, TablerIcons.Plane, ServiceType.TRAVEL),
        QuickActionItem(R.string.service_rent_a_car, TablerIcons.Car, ServiceType.RENT_A_CAR),
        QuickActionItem(R.string.service_food, TablerIcons.ShoppingCart, ServiceType.FOOD)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.Medium),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable(enabled = !isLoading) { onServiceClick(item.serviceType) }
                    .padding(vertical = Spaces.ExtraSmall)
            ) {
                ShimmerWrapper(
                    isLoading = isLoading,
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(18.dp))
                ) {
                    RoundedIconButton(
                        icon = item.icon,
                        onClick = { onServiceClick(item.serviceType) },
                        size = 60.dp,
                        borderRadius = 18.dp,
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        iconSize = 26.dp
                    )
                }

                Spacer(modifier = Modifier.height(Spaces.Small))

                ShimmerWrapper(
                    isLoading = isLoading,
                    modifier = Modifier.width(56.dp).height(14.dp)
                ) {
                    Text(
                        text = stringResource(item.titleRes),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
