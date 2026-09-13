package com.example.baltazar.feature.company.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.enums.getDisplayNameResId
import compose.icons.TablerIcons
import compose.icons.tablericons.Building
import compose.icons.tablericons.ChevronRight
import compose.icons.tablericons.MapPin
import compose.icons.tablericons.Star

@Composable
fun CompanyCard(
    name: String,
    logoUrl: String?,
    coverImageUrl: String?,
    rating: Double,
    reviewCount: Int,
    category: String,
    address: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(BorderRadiuses.Medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Rounded Company Logo/Avatar
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(BorderRadiuses.Medium))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                val imageSource = logoUrl ?: coverImageUrl
                if (!imageSource.isNullOrBlank()) {
                    AsyncImage(
                        model = imageSource,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(60.dp)
                    )
                } else {
                    Icon(
                        imageVector = TablerIcons.Building,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(IconSizes.Large)
                    )
                }
            }

            Spacer(modifier = Modifier.width(Spaces.Medium))

            // Middle: Details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spaces.ExtraMini)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (category.isNotBlank()) {
                    val serviceType = ServiceType.fromRaw(category)
                    Text(
                        text = stringResource(id = serviceType.getDisplayNameResId()),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (!address.isNullOrBlank()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spaces.ExtraMini)
                    ) {
                        Icon(
                            imageVector = TablerIcons.MapPin,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(IconSizes.ExtraSmall)
                        )
                        Text(
                            text = address,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(Spaces.Small))

            // Right: Rating badge & arrow indicator
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(Spaces.Small)
            ) {
                if (rating > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spaces.ExtraMini),
                        modifier = Modifier
                            .clip(RoundedCornerShape(BorderRadiuses.Small))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                            .padding(horizontal = Paddings.SmallMinus, vertical = Paddings.ExtraMini)
                    ) {
                        Icon(
                            imageVector = TablerIcons.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(IconSizes.ExtraSmall)
                        )
                        Text(
                            text = String.format("%.1f", rating),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Icon(
                    imageVector = TablerIcons.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(IconSizes.Medium)
                )
            }
        }
    }
}

