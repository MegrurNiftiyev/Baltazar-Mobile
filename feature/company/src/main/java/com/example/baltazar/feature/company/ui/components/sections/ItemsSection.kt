package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.SectionTitle
import com.example.baltazar.core.core.components.ServiceItemCard
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.feature.company.domain.model.RelatedItem

@Composable
fun ItemsSection(
    items: List<RelatedItem>,
    modifier: Modifier = Modifier,
    onItemClick: ((RelatedItem) -> Unit)? = null,
    onFavoriteClick: ((RelatedItem, Boolean) -> Unit)? = null
) {
    if (items.isEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spaces.Small)
    ) {
        SectionTitle(title = stringResource(id = R.string.company_offers_title))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Spaces.Medium)
        ) {
            items(items, key = { it.id }) { item ->
                ServiceItemCard(
                    title = item.title,
                    subtitle = item.category,
                    image = item.imageUrl,
                    price = item.price,
                    currency = item.currency,
                    priceSuffix = item.priceSuffix,
                    rating = if (item.rating > 0) item.rating else null,
                    isFavorite = item.isLiked,
                    onFavoriteClick = { isFav -> onFavoriteClick?.invoke(item, isFav) },
                    onClick = { onItemClick?.invoke(item) }
                )
            }
        }
    }
}

