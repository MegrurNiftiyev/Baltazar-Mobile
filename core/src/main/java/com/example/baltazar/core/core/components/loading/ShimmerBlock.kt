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

/**
 * Tək bir düzbucaqlı shimmer placeholder bloku.
 *
 * Bu, layihə boyu təkrarlanan pattern-i əvəz edir:
 * ```
 * ShimmerWrapper(isLoading = true, modifier = Modifier.fillMaxWidth(x).height(y).clip(...)) {
 *     Box(modifier = Modifier.fillMaxWidth().height(y).background(surfaceContainerHigh))
 * }
 * ```
 * Görünüş/animasiya eynidir — sadəcə təkrarlanan kodu bir yerə yığır.
 */
@Composable
fun ShimmerBlock(
    height: Dp,
    modifier: Modifier = Modifier,
    widthFraction: Float = 1f,
    shape: Shape = RoundedCornerShape(BorderRadiuses.Small)
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
