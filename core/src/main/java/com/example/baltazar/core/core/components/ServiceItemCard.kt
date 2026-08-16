package com.example.baltazar.core.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil.compose.AsyncImage
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.extensions.formatPrice
import com.example.baltazar.core.core.utils.requireLoadedFields

@Composable
fun ServiceItemCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    image: String? = null,
    title: String? = null,
    subtitle: String? = null,
    price: Double? = null,
    currency: String? = "AZN",
    priceSuffix: String? = null,
    rating: Double? = null
) {
    if (!isLoading) {
        requireLoadedFields(
            "ServiceItemCard",
            "image" to image,
            "title" to title,
            "price" to price
        )
    }

    Card(
        shape = RoundedCornerShape(BorderRadiuses.Large),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .width(220.dp)
            .clickable(enabled = !isLoading, onClick = onClick)
    ) {
        Column {
            // Image slot — real content sizes itself; shimmer matches that size while loading
            ShimmerWrapper(
                isLoading = isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(topStart = BorderRadiuses.Large, topEnd = BorderRadiuses.Large))
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(130.dp)) {
                    AsyncImage(
                        model = image,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(130.dp)
                    )
                    rating?.let { r ->
                        RatingBadge(
                            rating = r,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(Paddings.Small)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(Paddings.Small)) {
                // Title slot
                ShimmerWrapper(
                    isLoading = isLoading,
                    modifier = Modifier.fillMaxWidth(0.85f).height(18.dp)
                ) {
                    Text(
                        text = title.orEmpty(),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (!subtitle.isNullOrBlank() || isLoading) {
                    Spacer(modifier = Modifier.height(2.dp))
                    // Subtitle slot
                    ShimmerWrapper(
                        isLoading = isLoading,
                        modifier = Modifier.fillMaxWidth(0.6f).height(14.dp)
                    ) {
                        Text(
                            text = subtitle.orEmpty(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spaces.Small))

                // Price slot
                ShimmerWrapper(
                    isLoading = isLoading,
                    modifier = if (isLoading) Modifier.width(90.dp).height(18.dp) else Modifier
                ) {
                    Text(
                        text = if (price != null) formatPrice(price, currency.orEmpty(), priceSuffix.orEmpty()) else "",
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
