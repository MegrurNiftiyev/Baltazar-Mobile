package com.example.baltazar.core.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.domain.model.ReviewEligibility
import com.example.baltazar.core.domain.model.ReviewItem

@Composable
fun ReviewSection(
    reviews: List<ReviewItem>,
    rating: Double,
    reviewCount: Int,
    reviewEligibility: ReviewEligibility?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isSubmittingReview: Boolean = false,
    onSubmitReview: (Int, String) -> Unit
) {
    var showWriteDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        SectionTitle(
            title = if (reviewCount > 0) "${stringResource(R.string.reviews_title)} ($reviewCount)" else stringResource(R.string.reviews_title),
            actionText = if (reviewEligibility?.canSubmit == true) stringResource(R.string.write_review) else null,
            onActionClick = { showWriteDialog = true }
        )

        Spacer(modifier = Modifier.height(Spaces.Small))

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
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = Paddings.Small)
            )
        }

        if (reviewEligibility != null && !reviewEligibility.canSubmit && !reviewEligibility.alreadyReviewed && !isLoading) {
            Spacer(modifier = Modifier.height(Spaces.Small))
            ReviewIneligibleNotice()
        }
    }

    if (showWriteDialog) {
        WriteReviewDialog(
            isSubmitting = isSubmittingReview,
            onDismiss = { showWriteDialog = false },
            onSubmit = { score, comment ->
                onSubmitReview(score, comment)
                showWriteDialog = false
            }
        )
    }
}
