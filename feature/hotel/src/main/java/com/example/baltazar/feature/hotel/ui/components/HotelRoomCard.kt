package com.example.baltazar.feature.hotel.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.extensions.formatPrice
import com.example.baltazar.feature.hotel.domain.model.HotelRoom
import compose.icons.TablerIcons
import compose.icons.tablericons.CircleCheck
import compose.icons.tablericons.User

@Composable
fun HotelRoomCard(
    room: HotelRoom,
    priceSuffix: String = "/ gecə",
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(BorderRadiuses.Medium)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .then(
                if (isSelected) {
                    Modifier
                        .border(width = BorderRadiuses.ExtraMini, color = MaterialTheme.colorScheme.primary, shape = shape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
                } else {
                    Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow)
                }
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            )
            .padding(Paddings.Small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = room.image,
                contentDescription = room.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(IconSizes.Max + Spaces.LargePlus)
                    .clip(RoundedCornerShape(BorderRadiuses.Small))
            )

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(Paddings.Mini)
                        .size(IconSizes.Medium)
                        .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = TablerIcons.CircleCheck,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(IconSizes.Small)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(Spaces.Medium))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spaces.ExtraMini)
        ) {
            Text(
                text = room.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = TablerIcons.User,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(IconSizes.MediumMinus)
                )
                Spacer(modifier = Modifier.width(Spaces.ExtraMini))
                Text(
                    text = "${room.capacity} nəfərlik",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (room.amenities.isNotEmpty()) {
                Text(
                    text = room.amenities.take(2).joinToString(" • "),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = formatPrice(room.price, "AZN", priceSuffix),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun HotelRoomCardShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadiuses.Medium))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(Paddings.Small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShimmerWrapper(
            isLoading = true,
            modifier = Modifier
                .size(IconSizes.Max + Spaces.LargePlus)
                .clip(RoundedCornerShape(BorderRadiuses.Small))
        ) {
            Box(
                modifier = Modifier
                    .size(IconSizes.Max + Spaces.LargePlus)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            )
        }

        Spacer(modifier = Modifier.width(Spaces.Medium))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spaces.Small)
        ) {
            ShimmerWrapper(
                isLoading = true,
                modifier = Modifier
                    .width(Spaces.Max * 2 + Spaces.LargeMinus)
                    .height(Spaces.MediumPlus)
                    .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Spaces.MediumPlus)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                )
            }

            ShimmerWrapper(
                isLoading = true,
                modifier = Modifier
                    .width(Spaces.Max + Spaces.LargeMinus)
                    .height(Spaces.MediumMinus)
                    .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Spaces.MediumMinus)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                )
            }

            ShimmerWrapper(
                isLoading = true,
                modifier = Modifier
                    .width(Spaces.Max + Spaces.GiantMinus)
                    .height(Spaces.MediumPlus)
                    .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Spaces.MediumPlus)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                )
            }
        }
    }
}
