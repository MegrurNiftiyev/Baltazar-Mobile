package com.example.baltazar.core.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.example.baltazar.core.core.extensions.shimmerEffect

@Composable
fun ShimmerWrapper(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        // Real content is always composed (so it determines the box's size),
        // just made invisible while loading — never removed from the tree.
        Box(modifier = Modifier.alpha(if (isLoading) 0f else 1f)) {
            content()
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .shimmerEffect()
            )
        }
    }
}
