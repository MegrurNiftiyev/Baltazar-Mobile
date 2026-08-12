package com.example.baltazar.feature.explore.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.extensions.autoShimmer
import com.example.baltazar.core.core.extensions.shimmerEffect
import com.example.baltazar.core.enums.ServiceType
import com.example.baltazar.feature.explore.domain.model.BannerItem

@Composable
fun BannerCarousel(
    banners: List<BannerItem>,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onBannerClick: (BannerItem) -> Unit = {}
) {
    val displayBanners = if (isLoading || banners.isEmpty()) {
        listOf(
            BannerItem(
                id = "mock1",
                serviceType = ServiceType.UNKNOWN,
                title = "Mock Banner Title Long",
                desc = "Mock Banner Description Much Longer Mock",
                image = "",
                order = 0,
                isActive = false,
                createdAt = ""
            )
        )
    } else {
        banners
    }

    val pagerState = rememberPagerState(pageCount = { displayBanners.size })

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) { page ->
            val banner = displayBanners[page]
            BannerCard(
                banner = banner,
                isLoading = isLoading,
                onClick = { if (!isLoading) onBannerClick(banner) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Paddings.Large)
            )
        }

        if (displayBanners.size > 1 && !isLoading) {
            Spacer(modifier = Modifier.height(Spaces.Small))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = Spaces.Small)
            ) {
                repeat(displayBanners.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    val width by animateDpAsState(
                        targetValue = if (isSelected) 18.dp else 7.dp,
                        label = "DotWidth"
                    )
                    val color by animateColorAsState(
                        targetValue = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        },
                        label = "DotColor"
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .height(7.dp)
                            .width(width)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
        }
    }
}

@Composable
private fun BannerCard(
    banner: BannerItem,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isImageLoading by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(BorderRadiuses.Large),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .clickable(enabled = !isLoading) { onClick() }
            .autoShimmer(isLoading, cornerRadius = 16)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (!isLoading) {
                if (isImageLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                }
                AsyncImage(
                    model = banner.image,
                    contentDescription = banner.title,
                    contentScale = ContentScale.Crop,
                    onState = { state ->
                        isImageLoading = state is AsyncImagePainter.State.Loading
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            val overlayModifier = if (isLoading) {
                Modifier.background(Color.Transparent)
            } else {
                Modifier.background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.82f)
                        ),
                        startY = 60f
                    )
                )
            }

            // Gradient overlay at bottom for crisp readable text
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(overlayModifier)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(Paddings.Large)
            ) {
                Text(
                    text = banner.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = if (isLoading) Color.Transparent else Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.autoShimmer(isLoading)
                )
                Spacer(modifier = Modifier.height(Spaces.Mini))
                Text(
                    text = banner.desc,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isLoading) Color.Transparent else Color.White.copy(alpha = 0.9f)
                    ),
                    modifier = Modifier.autoShimmer(isLoading)
                )
            }
        }
    }
}
