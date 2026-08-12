package com.example.baltazar.feature.explore.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.core.components.CustomTextButton
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.extensions.autoShimmer
import com.example.baltazar.core.enums.ServiceType
import com.example.baltazar.feature.explore.domain.model.ExploreItem
import com.example.baltazar.feature.explore.domain.model.ExploreSection

@Composable
fun ExploreSectionRow(
    section: ExploreSection,
    isLoading: Boolean,
    onItemClick: (ExploreItem) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displaySection = if (isLoading) {
        ExploreSection(
            title = "Mock Section Title Text",
            serviceType = ServiceType.UNKNOWN,
            order = 0,
            items = List(3) {
                ExploreItem(
                    id = "mock$it",
                    serviceType = ServiceType.UNKNOWN,
                    serviceId = "",
                    title = "Mock Item Title",
                    image = "",
                    price = 0.0,
                    priceSuffix = "",
                    currency = "",
                    rating = 0.0,
                    ratingCount = 0,
                    category = "Mock Category"
                )
            }
        )
    } else {
        section
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.Large),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = displaySection.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isLoading) Color.Transparent else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.autoShimmer(isLoading)
            )
            
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
            items(displaySection.items, key = { it.id }) { item ->
                ServiceItemCard(
                    imageUrl = item.image,
                    title = item.title,
                    subtitle = item.category ?: item.priceSuffix,
                    price = item.price,
                    rating = if (item.rating > 0) item.rating else null,
                    isLoading = isLoading,
                    onClick = { if (!isLoading) onItemClick(item) }
                )
            }
        }
    }
}
