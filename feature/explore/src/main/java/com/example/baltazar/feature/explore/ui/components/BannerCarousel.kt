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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.feature.explore.domain.model.BannerItem
import compose.icons.TablerIcons
import compose.icons.tablericons.CloudOff

@Composable
fun BannerCarousel(
    banners: List<BannerItem>,
    isLoading: Boolean,
    isError: Boolean = false,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onBannerClick: (BannerItem) -> Unit = {}
) {
    if (isError && !isLoading) {
        Card(
            shape = RoundedCornerShape(BorderRadiuses.Large),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            modifier = modifier
                .fillMaxWidth()
                .height(210.dp)
                .padding(horizontal = Paddings.Large)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Paddings.Medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = TablerIcons.CloudOff,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(Spaces.Small))
                Text(
                    text = stringResource(R.string.explore_error_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spaces.Mini))
                Text(
                    text = stringResource(R.string.explore_error_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (onRetry != null) {
                    Spacer(modifier = Modifier.height(Spaces.Small))
                    RoundedButton(
                        text = stringResource(R.string.explore_retry),
                        onClick = onRetry,
                        modifier = Modifier.width(140.dp)
                    )
                }
            }
        }
        return
    }

    val displayBanners = if (isLoading) {
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

    if (displayBanners.isEmpty()) return

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
    Card(
        shape = RoundedCornerShape(BorderRadiuses.Large),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.clickable(enabled = !isLoading) { onClick() }
    ) {
        ShimmerWrapper(
            isLoading = isLoading,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = banner.image,
                    contentDescription = banner.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                val overlayModifier = Modifier.background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.82f)
                        ),
                        startY = 60f
                    )
                )

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
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(Spaces.Mini))
                    Text(
                        text = banner.desc,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                }
            }
        }
    }
}
