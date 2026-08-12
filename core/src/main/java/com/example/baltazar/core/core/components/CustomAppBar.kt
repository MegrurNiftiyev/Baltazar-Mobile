package com.example.baltazar.core.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.enums.TitleAlignment

@Composable
fun CustomAppBar(
    title: String,
    modifier: Modifier = Modifier,
    alignment: TitleAlignment = TitleAlignment.START,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Paddings.Large)
        ) {
            if (leadingContent != null) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    leadingContent()
                }
            }

            val titleAlignment = when (alignment) {
                TitleAlignment.START -> Alignment.CenterStart
                TitleAlignment.CENTER -> Alignment.Center
                TitleAlignment.END -> Alignment.CenterEnd
            }

            val titlePadding = if (alignment == TitleAlignment.START && leadingContent != null) {
                Modifier.padding(start = 48.dp)
            } else if (alignment == TitleAlignment.END && trailingContent != null) {
                Modifier.padding(end = 48.dp)
            } else {
                Modifier
            }

            Text(
                text = title,
                modifier = Modifier
                    .align(titleAlignment)
                    .then(titlePadding),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            if (trailingContent != null) {
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    trailingContent()
                }
            }
        }
    }
}
