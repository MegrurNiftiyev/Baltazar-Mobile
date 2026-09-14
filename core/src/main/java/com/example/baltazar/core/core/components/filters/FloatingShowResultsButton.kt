package com.example.baltazar.core.core.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.Paddings

@Composable
fun FloatingShowResultsButton(
    isVisible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.Medium, vertical = Paddings.Small),
            contentAlignment = Alignment.Center
        ) {
            RoundedButton(
                onClick = onClick,
                text = stringResource(id = R.string.show_results),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
