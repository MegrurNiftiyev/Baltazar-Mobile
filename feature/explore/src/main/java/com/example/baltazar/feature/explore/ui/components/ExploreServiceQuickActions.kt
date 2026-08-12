package com.example.baltazar.feature.explore.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedIconButton
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.extensions.autoShimmer
import com.example.baltazar.core.enums.ServiceType
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
            .padding(horizontal = Paddings.Large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(enabled = !isLoading) { onServiceClick(item.serviceType) }
                    .padding(Spaces.ExtraSmall)
            ) {
                RoundedIconButton(
                    icon = item.icon,
                    onClick = { onServiceClick(item.serviceType) },
                    size = 64.dp,
                    borderRadius = 20.dp,
                    containerColor = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.surfaceContainerHigh,
                    contentColor = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurface,
                    iconSize = 28.dp,
                    modifier = Modifier.autoShimmer(isLoading)
                )
                Spacer(modifier = Modifier.height(Spaces.Small))
                Text(
                    text = if (isLoading) "Loading" else stringResource(item.titleRes),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.autoShimmer(isLoading)
                )
            }
        }
    }
}
