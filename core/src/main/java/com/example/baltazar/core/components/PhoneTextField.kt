package com.example.baltazar.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.constants.BorderRadiuses
import com.example.baltazar.core.constants.IconSizes
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.enums.CornerShape
import com.example.baltazar.core.enums.CountryPrefix

@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    selectedPrefix: CountryPrefix,
    onPrefixSelected: (CountryPrefix) -> Unit,
    dropdownIcon: ImageVector,
    modifier: Modifier = Modifier,
    prefixes: List<CountryPrefix> = CountryPrefix.entries,
    label: String? = null,
    placeholder: String? = null,
    errorText: String? = null,
    enabled: Boolean = true,
    shape: CornerShape = CornerShape.Rounded,
    borderRadius: Dp = BorderRadiuses.Medium,
    dropdownIconSize: Dp = IconSizes.ExtraSmall,
    dropdownIconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    prefixTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    disabledBorderColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    var expanded by remember { mutableStateOf(false) }

    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        errorText = errorText,
        enabled = enabled,
        singleLine = true,
        keyboardType = KeyboardType.Phone,
        shape = shape,
        borderRadius = borderRadius,
        leadingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = enabled,
                        onClick = { expanded = true }
                    )
                    .padding(start = Paddings.Small)
            ) {
                Text(text = selectedPrefix.flagEmoji, style = prefixTextStyle)
                Spacer(modifier = Modifier.width(Paddings.ExtraMini))
                Text(
                    text = selectedPrefix.dialCode,
                    style = prefixTextStyle,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(Paddings.ExtraMini))
                Icon(
                    imageVector = dropdownIcon,
                    contentDescription = null,
                    modifier = Modifier.size(dropdownIconSize),
                    tint = dropdownIconTint
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    prefixes.forEach { prefix ->
                        DropdownMenuItem(
                            text = { Text("${prefix.flagEmoji}  ${prefix.dialCode}") },
                            onClick = {
                                onPrefixSelected(prefix)
                                expanded = false
                            }
                        )
                    }
                }
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