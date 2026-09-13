package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.ReviewCard
import com.example.baltazar.core.core.components.SectionTitle
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.domain.model.ReviewItem
import compose.icons.TablerIcons
import compose.icons.tablericons.Star

@Composable
fun ReviewsSection(
    rating: Double,
    reviewCount: Int,
    reviews: List<ReviewItem> = emptyList(),
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spaces.Small)
    ) {
        SectionTitle(title = stringResource(id = R.string.company_reviews_title))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spaces.Small)
        ) {
            Icon(
                imageVector = TablerIcons.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(IconSizes.Medium)
            )

            Text(
                text = stringResource(id = R.string.company_reviews_score, rating),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stringResource(id = R.string.company_reviews_count, reviewCount),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(Spaces.ExtraMini))

        if (isLoading) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(Spaces.Small),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                repeat(2) {
                    item {
                        ShimmerWrapper(
                            isLoading = true,
                            modifier = Modifier
                                .width(280.dp)
                                .height(110.dp)
                                .clip(RoundedCornerShape(BorderRadiuses.Medium))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            )
                        }
                    }
                }
            }
        } else if (reviews.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(Spaces.Small),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                items(reviews, key = { it.id }) { review ->
                    ReviewCard(review = review)
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_reviews_yet),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = Paddings.Small)
            )
        }
    }
}

