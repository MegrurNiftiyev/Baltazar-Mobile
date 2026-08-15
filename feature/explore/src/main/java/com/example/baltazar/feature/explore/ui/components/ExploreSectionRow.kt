package com.example.baltazar.feature.explore.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.components.CustomTextButton
import com.example.baltazar.core.core.components.ServiceItemCard
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.domain.model.ServiceCardItem
import com.example.baltazar.feature.explore.domain.model.ExploreSection

@Composable
fun ExploreSectionRow(
    section: ExploreSection,
    isLoading: Boolean,
    onItemClick: (ServiceCardItem) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.Large),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerWrapper(
                isLoading = isLoading,
                modifier = Modifier.width(160.dp).height(24.dp)
            ) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            if (!isLoading) {
                CustomTextButton(
                    text = "See all",
                    onClick = onSeeAllClick
                )
            }
        }

        Spacer(modifier = Modifier.height(Spaces.Small))

        LazyRow(
            contentPadding = PaddingValues(horizontal = Paddings.Large),
            horizontalArrangement = Arrangement.spacedBy(Spaces.Medium),
            userScrollEnabled = !isLoading
        ) {
            if (isLoading) {
                items(3) {
                    ServiceItemCard(
                        isLoading = true,
                        onClick = {}
                    )
                }
            } else {
                items(section.items, key = { it.id }) { item ->
                    ServiceItemCard(
                        isLoading = false,
                        onClick = { onItemClick(item) },
                        image = item.image,
                        title = item.title,
                        subtitle = item.category,
                        price = item.price,
                        currency = item.currency,
                        priceSuffix = item.priceSuffix,
                        rating = if (item.rating > 0) item.rating else null
                    )
                }
            }
        }
    }
}
