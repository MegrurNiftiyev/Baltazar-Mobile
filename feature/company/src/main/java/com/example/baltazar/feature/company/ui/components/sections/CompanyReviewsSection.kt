package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.baltazar.core.core.components.SectionTitle
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Spaces
import compose.icons.TablerIcons
import compose.icons.tablericons.Star

@Composable
fun CompanyReviewsSection(
    rating: Double,
    reviewCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spaces.Small)
    ) {
        SectionTitle(title = "Rəylər və Qiymətləndirmə")

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
                text = String.format("%.1f / 5.0", rating),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "($reviewCount rəy)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
