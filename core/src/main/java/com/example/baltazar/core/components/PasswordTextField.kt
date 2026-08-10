package com.example.baltazar.core.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.constants.BorderRadiuses
import com.example.baltazar.core.constants.IconSizes
import com.example.baltazar.core.enums.CornerShape

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    errorText: String? = null,
    enabled: Boolean = true,
    shape: CornerShape = CornerShape.Rounded,
    borderRadius: Dp = BorderRadiuses.Medium,
    leadingIcon: @Composable (() -> Unit)? = null,
    visibleIcon: ImageVector,
    hiddenIcon: ImageVector,
    trailingIconSize: Dp = IconSizes.Small,
    trailingIconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    disabledBorderColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    var isVisible by remember { mutableStateOf(false) }

    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        errorText = errorText,
        enabled = enabled,
        singleLine = true,
        keyboardType = KeyboardType.Password,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = shape,
        borderRadius = borderRadius,
        leadingIcon = leadingIcon,
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    imageVector = if (isVisible) visibleIcon else hiddenIcon,
                    contentDescription = null,
                    modifier = Modifier.size(trailingIconSize),
                    tint = trailingIconTint
                )
            }
        },
        textStyle = textStyle,
        containerColor = containerColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        errorBorderColor = errorBorderColor,
        disabledBorderColor = disabledBorderColor
    )
}