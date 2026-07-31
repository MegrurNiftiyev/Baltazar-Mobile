package com.example.baltazar.core.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.constants.BorderRadiuses
import com.example.baltazar.core.constants.Paddings


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    errorText: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    borderRadius: Dp = BorderRadiuses.Medium,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    disabledBorderColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    val isError = errorText != null

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = singleLine,
            isError = isError,
            label = label?.let { { Text(it) } },
            placeholder = placeholder?.let { { Text(it) } },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = textStyle,
            shape = RoundedCornerShape(borderRadius),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
                errorBorderColor = errorBorderColor,
                disabledBorderColor = disabledBorderColor
            )
        )

        if (isError) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.bodySmall,
                color = errorBorderColor,
                modifier = Modifier.padding(start = Paddings.Medium, top = Paddings.ExtraMini)
            )
        }
    }
}


