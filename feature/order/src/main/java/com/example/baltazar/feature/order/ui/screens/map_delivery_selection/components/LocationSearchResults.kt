package com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.feature.order.domain.model.LocationSearchResult

@Composable
fun LocationSearchResults(
    results: List<LocationSearchResult>,
    onResultSelected: (LocationSearchResult) -> Unit,
    modifier: Modifier = Modifier
) {
    if (results.isEmpty()) return

    Surface(
        shape = RoundedCornerShape(BorderRadiuses.Large),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = Spaces.Medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Paddings.Small)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = Paddings.Max * 5)
        ) {
            items(results) { result ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onResultSelected(result) }
                        .padding(Paddings.Medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(IconSizes.Medium)
                    )
                    Spacer(modifier = Modifier.width(Spaces.Small))
                    Text(
                        text = result.addressName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
