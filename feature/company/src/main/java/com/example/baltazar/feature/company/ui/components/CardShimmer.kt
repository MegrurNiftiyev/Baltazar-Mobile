package com.example.baltazar.feature.company.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.components.ShimmerBlock
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun CardShimmer(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(BorderRadiuses.Medium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Spaces.ExtraMini)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ShimmerBlock(
                height = 140.dp,
                shape = RoundedCornerShape(topStart = BorderRadiuses.Medium, topEnd = BorderRadiuses.Medium)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.Medium),
                verticalArrangement = Arrangement.spacedBy(Spaces.Small)
            ) {
                ShimmerBlock(widthFraction = 0.6f, height = Spaces.MediumPlus)
                ShimmerBlock(widthFraction = 0.4f, height = Spaces.Small)
            }
        }
    }
}
