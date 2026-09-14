package com.example.baltazar.core.core.components.internal

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.baltazar.core.core.constants.Paddings

@Composable
internal fun FieldErrorText(
    errorText: String?,
    errorColor: Color = MaterialTheme.colorScheme.error,
    modifier: Modifier = Modifier
) {
    if (errorText != null) {
        Text(
            text = errorText,
            style = MaterialTheme.typography.bodySmall,
            color = errorColor,
            modifier = modifier.padding(start = Paddings.Medium, top = Paddings.ExtraMini)
        )
    }
}
