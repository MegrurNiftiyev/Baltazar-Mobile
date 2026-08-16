package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.SectionTitle
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun GallerySection(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    if (images.isEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spaces.Small)
    ) {
        SectionTitle(title = stringResource(id = R.string.company_gallery_title))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Spaces.Medium),
            contentPadding = PaddingValues(horizontal = Paddings.ExtraMini)
        ) {
            items(images) { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(160.dp)
                        .height(110.dp)
                        .clip(RoundedCornerShape(BorderRadiuses.Medium))
                )
            }
        }
    }
}
