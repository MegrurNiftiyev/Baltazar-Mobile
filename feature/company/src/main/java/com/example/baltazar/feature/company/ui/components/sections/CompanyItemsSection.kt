package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.components.SectionTitle
import com.example.baltazar.core.core.components.StandardItemCard
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.feature.company.domain.model.RelatedItem

@Composable
fun CompanyItemsSection(
    items: List<RelatedItem>,
    modifier: Modifier = Modifier,
    onItemClick: ((RelatedItem) -> Unit)? = null
) {
    if (items.isEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spaces.Small)
    ) {
        SectionTitle(title = "Təkliflər / Məhsullar")

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Spaces.Medium)
        ) {
            items(items, key = { it.id }) { item ->
                StandardItemCard(
                    title = item.title,
                    subtitle = item.category,
                    imageUrl = item.imageUrl,
                    price = item.price,
                    currency = item.currency,
                    priceSuffix = item.priceSuffix,
                    rating = item.rating,
                    cardViewMode = CardViewMode.GRID,
                    onClick = { onItemClick?.invoke(item) },
                    modifier = Modifier.width(180.dp)
                )
            }
        }
    }
}
