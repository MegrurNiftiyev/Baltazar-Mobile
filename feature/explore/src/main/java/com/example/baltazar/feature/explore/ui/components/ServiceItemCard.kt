package com.example.baltazar.feature.explore.ui.components

import androidx.compose.foundation.background
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
import coil.compose.AsyncImagePainter
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.extensions.autoShimmer
import com.example.baltazar.core.core.extensions.shimmerEffect

@Composable
fun ServiceItemCard(
    imageUrl: String,
    title: String,
    subtitle: String,
    price: Double,
    rating: Double? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isImageLoading by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(BorderRadiuses.Large),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .width(220.dp)
            .clickable(enabled = !isLoading, onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(topStart = BorderRadiuses.Large, topEnd = BorderRadiuses.Large))
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .shimmerEffect()
                    )
                } else {
                    if (isImageLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .shimmerEffect()
                        )
                    }

                    AsyncImage(
                        model = imageUrl,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        onState = { state ->
                            isImageLoading = state is AsyncImagePainter.State.Loading
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
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
                Text(
                    text = if (isLoading) "Very Long Mock Title Text" else title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.autoShimmer(isLoading)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isLoading) "Mock Subtitle Text" else subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.autoShimmer(isLoading)
                )
                Spacer(modifier = Modifier.height(Spaces.Small))
                Text(
                    text = if (isLoading) "$999" else "$${price.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.autoShimmer(isLoading)
                )
            }
        }
    }
}
