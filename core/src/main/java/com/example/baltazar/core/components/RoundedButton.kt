package com.example.baltazar.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.constants.BorderRadiuses
import com.example.baltazar.core.constants.IconSizes
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces

@Composable
fun RoundedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderRadius: Dp = _root_ide_package_.com.example.baltazar.core.constants.BorderRadiuses.Medium,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContainerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(
        defaultElevation = _root_ide_package_.com.example.baltazar.core.constants.Spaces.Mini,
        pressedElevation = _root_ide_package_.com.example.baltazar.core.constants.Spaces.Tiny
    ),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(_root_ide_package_.com.example.baltazar.core.constants.Spaces.GiantPlus),
        shape = RoundedCornerShape(borderRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        elevation = elevation
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(_root_ide_package_.com.example.baltazar.core.constants.IconSizes.Medium),
                    color = contentColor,
                    strokeWidth = _root_ide_package_.com.example.baltazar.core.constants.Paddings.ExtraMini
                )
            } else {
                Text(
                    text = text,
                    style = textStyle
                )
            }
        }
    }
}