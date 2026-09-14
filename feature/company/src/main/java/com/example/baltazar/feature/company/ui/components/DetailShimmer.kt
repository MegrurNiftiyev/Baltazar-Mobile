package com.example.baltazar.feature.company.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.components.ShimmerBlock
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun DetailShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Paddings.Large),
        verticalArrangement = Arrangement.spacedBy(Spaces.Medium)
    ) {
        ShimmerBlock(height = 180.dp, shape = RoundedCornerShape(BorderRadiuses.Medium))
        ShimmerBlock(widthFraction = 0.6f, height = Spaces.ExtraLarge)
        ShimmerBlock(widthFraction = 0.4f, height = Spaces.Medium)
        ShimmerBlock(height = 120.dp, shape = RoundedCornerShape(BorderRadiuses.Medium))
    }
}
