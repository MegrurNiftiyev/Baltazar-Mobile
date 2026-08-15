package com.example.baltazar.feature.rentacar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import compose.icons.tablericons.GasStation
import compose.icons.tablericons.ManualGearbox
import compose.icons.tablericons.Users

@Composable
fun CarSpecsGrid(
    transmission: String,
    fuelType: String,
    seats: Int,
    year: Int,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spaces.Small)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spaces.Small)
        ) {
            SpecCard(
                icon = TablerIcons.ManualGearbox,
                label = stringResource(R.string.transmission_label),
                value = transmission.ifBlank { "-" },
                isLoading = isLoading,
                modifier = Modifier.weight(1f)
            )
            SpecCard(
                icon = TablerIcons.GasStation,
                label = stringResource(R.string.fuel_label),
                value = fuelType.ifBlank { "-" },
                isLoading = isLoading,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spaces.Small)
        ) {
            SpecCard(
                icon = TablerIcons.Users,
                label = stringResource(R.string.seats_label),
                value = if (seats > 0) stringResource(R.string.seats_format, seats) else "-",
                isLoading = isLoading,
                modifier = Modifier.weight(1f)
            )
            SpecCard(
                icon = TablerIcons.Calendar,
                label = stringResource(R.string.year_label),
                value = if (year > 0) year.toString() else "-",
                isLoading = isLoading,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SpecCard(
    icon: ImageVector,
    label: String,
    value: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        ShimmerWrapper(
            isLoading = true,
            modifier = modifier
                .fillMaxWidth()
                .height(Spaces.Max + Spaces.Mini)
                .clip(RoundedCornerShape(BorderRadiuses.Medium))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            )
        }
    } else {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(BorderRadiuses.Medium))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(Paddings.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(IconSizes.ExtraHuge)
                    .clip(RoundedCornerShape(BorderRadiuses.Small))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(IconSizes.LargeMinus)
                )
            }

            Spacer(modifier = Modifier.size(Spaces.Small))

            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
