package com.example.baltazar.feature.company.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerBlock(
                height = 60.dp,
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(BorderRadiuses.Medium)
            )

            Spacer(modifier = Modifier.width(Spaces.Medium))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spaces.ExtraMini)
            ) {
                ShimmerBlock(height = 18.dp, widthFraction = 0.7f)
                ShimmerBlock(height = 14.dp, widthFraction = 0.4f)
                ShimmerBlock(height = 14.dp, widthFraction = 0.5f)
            }

            Spacer(modifier = Modifier.width(Spaces.Small))

            ShimmerBlock(
                height = 24.dp,
                widthFraction = 0.2f,
                shape = RoundedCornerShape(BorderRadiuses.Small)
            )
        }
    }
}
