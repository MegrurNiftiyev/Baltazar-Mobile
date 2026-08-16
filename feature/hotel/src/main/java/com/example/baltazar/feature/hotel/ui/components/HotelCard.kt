package com.example.baltazar.feature.hotel.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.baltazar.core.core.components.RatingBadge
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.extensions.formatPrice
import com.example.baltazar.feature.hotel.domain.model.HotelItem
import compose.icons.TablerIcons
import compose.icons.tablericons.Heart
import compose.icons.tablericons.MapPin

@Composable
fun HotelCard(
    hotel: HotelItem?,
    isLoading: Boolean = false,
    cardViewMode: CardViewMode = CardViewMode.GRID,
    isFavorite: Boolean = false,
    onFavoriteClick: ((Boolean) -> Unit)? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var localFavorite by remember(isFavorite) { mutableStateOf(isFavorite) }

    Card(
        shape = RoundedCornerShape(BorderRadiuses.Medium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !isLoading, onClick = onClick)
    ) {
        if (cardViewMode == CardViewMode.GRID) {
            Column {
                ShimmerWrapper(
                    isLoading = isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(topStart = BorderRadiuses.Medium, topEnd = BorderRadiuses.Medium))
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                        AsyncImage(
                            model = hotel?.images?.firstOrNull(),
                            contentDescription = hotel?.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        hotel?.rating?.let { r ->
                            if (r > 0) {
                                RatingBadge(
                                    rating = r,
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(Paddings.SmallMinus)
                                )
                            }
                        }

                        if (!isLoading) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(Paddings.Mini)
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.35f))
                                    .clickable {
                                        localFavorite = !localFavorite
                                        onFavoriteClick?.invoke(localFavorite)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = TablerIcons.Heart,
                                    contentDescription = "Favorite",
                                    tint = if (localFavorite) Color(0xFFEF4444) else Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                Column(modifier = Modifier.padding(Paddings.SmallMinus)) {
                    if (!hotel?.city.isNullOrBlank() || isLoading) {
                        ShimmerWrapper(
                            isLoading = isLoading,
                            modifier = Modifier.fillMaxWidth(0.5f).height(14.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = TablerIcons.MapPin,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(Spaces.ExtraMini))
                                Text(
                                    text = hotel?.city.orEmpty(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(Spaces.ExtraMini))
                    }

                    ShimmerWrapper(
                        isLoading = isLoading,
                        modifier = Modifier.fillMaxWidth(0.85f).height(18.dp)
                    ) {
                        Text(
                            text = hotel?.title.orEmpty(),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(Spaces.SmallMinus))

                    ShimmerWrapper(
                        isLoading = isLoading,
                        modifier = if (isLoading) Modifier.width(90.dp).height(18.dp) else Modifier
                    ) {
                        Text(
                            text = if (hotel?.minPrice != null) formatPrice(hotel.minPrice, hotel.currency ?: "AZN", hotel.priceSuffix.orEmpty()) else "",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        } else {
            // List Mode - spacious format
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(138.dp)
            ) {
                ShimmerWrapper(
                    isLoading = isLoading,
                    modifier = Modifier
                        .width(130.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = BorderRadiuses.Medium, bottomStart = BorderRadiuses.Medium))
                ) {
                    Box(
                        modifier = Modifier
                            .width(130.dp)
                            .fillMaxHeight()
                    ) {
                        AsyncImage(
                            model = hotel?.images?.firstOrNull(),
                            contentDescription = hotel?.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        hotel?.rating?.let { r ->
                            if (r > 0) {
                                RatingBadge(
                                    rating = r,
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(Paddings.Tiny)
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(Paddings.Small),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                if (!hotel?.city.isNullOrBlank() || isLoading) {
                                    ShimmerWrapper(
                                        isLoading = isLoading,
                                        modifier = Modifier.fillMaxWidth(0.5f).height(12.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = TablerIcons.MapPin,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(Spaces.ExtraMini))
                                            Text(
                                                text = hotel?.city.orEmpty(),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(Spaces.ExtraMini))
                                }

                                ShimmerWrapper(
                                    isLoading = isLoading,
                                    modifier = Modifier.fillMaxWidth(0.9f).height(18.dp)
                                ) {
                                    Text(
                                        text = hotel?.title.orEmpty(),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            if (!isLoading) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                        .clickable {
                                            localFavorite = !localFavorite
                                            onFavoriteClick?.invoke(localFavorite)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = TablerIcons.Heart,
                                        contentDescription = "Favorite",
                                        tint = if (localFavorite) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        val amenitiesText = hotel?.amenities?.take(3)?.joinToString(" • ")
                        if (!amenitiesText.isNullOrBlank() || isLoading) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = amenitiesText.orEmpty(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    ShimmerWrapper(
                        isLoading = isLoading,
                        modifier = if (isLoading) Modifier.width(90.dp).height(18.dp) else Modifier
                    ) {
                        Text(
                            text = if (hotel?.minPrice != null) formatPrice(hotel.minPrice, hotel.currency ?: "AZN", hotel.priceSuffix.orEmpty()) else "",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
