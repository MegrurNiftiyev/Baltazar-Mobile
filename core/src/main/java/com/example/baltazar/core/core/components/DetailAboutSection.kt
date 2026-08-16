package com.example.baltazar.core.core.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun DetailAboutSection(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    maxCollapsedLines: Int = 3
) {
    if (description.isBlank() && !isLoading) return

    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        SectionTitle(title = title)

        Spacer(modifier = Modifier.height(Spaces.Small))

        if (isLoading) {
            Column(verticalArrangement = Arrangement.spacedBy(Spaces.ExtraMini)) {
                ShimmerWrapper(
                    isLoading = true,
                    modifier = Modifier
                        .fillMaxWidth()
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
                        .fillMaxWidth(0.9f)
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
                        .fillMaxWidth(0.6f)
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
            }
        } else {
            Column(modifier = Modifier.animateContentSize()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = if (isExpanded) Int.MAX_VALUE else maxCollapsedLines,
                    overflow = TextOverflow.Ellipsis
                )

                if (description.length > 120) {
                    Spacer(modifier = Modifier.height(Spaces.ExtraMini))
                    Text(
                        text = if (isExpanded) stringResource(R.string.read_less) else stringResource(R.string.read_more),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(BorderRadiuses.Small))
                            .clickable { isExpanded = !isExpanded }
                            .padding(vertical = Paddings.ExtraMini)
                    )
                }
            }
        }
    }
}
