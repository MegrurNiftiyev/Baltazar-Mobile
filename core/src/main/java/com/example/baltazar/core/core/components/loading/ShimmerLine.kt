package com.example.baltazar.core.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun ShimmerLine(
    widthFraction: Float = 1f,
    height: Dp = Spaces.MediumMinus,
    shape: Shape = RoundedCornerShape(BorderRadiuses.ExtraMini),
    modifier: Modifier = Modifier
) {
    ShimmerWrapper(
        isLoading = true,
        modifier = modifier
            .fillMaxWidth(widthFraction)
            .height(height)
            .clip(shape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
        )
    }
}
