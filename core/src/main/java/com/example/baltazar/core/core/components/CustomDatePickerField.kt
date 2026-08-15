package com.example.baltazar.core.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.enums.CornerShape
import com.example.baltazar.core.core.extensions.toShape
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerField(
    value: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = "YYYY-MM-DD",
    errorText: String? = null,
    enabled: Boolean = true,
    shape: CornerShape = CornerShape.Rounded,
    borderRadius: Dp = BorderRadiuses.Medium
) {
    var showDialog by remember { mutableStateOf(false) }
    val isError = errorText != null
    val datePickerState = rememberDatePickerState()

    Box(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = isError,
                label = label?.let { { Text(it) } },
                placeholder = placeholder?.let { { Text(it) } },
                trailingIcon = {
                    Icon(
                        imageVector = TablerIcons.Calendar,
                        contentDescription = "Select Date",
                        tint = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                },
                shape = shape.toShape(borderRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    errorBorderColor = MaterialTheme.colorScheme.error
                )
            )

            if (isError) {
                Text(
                    text = errorText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = Paddings.Medium, top = Paddings.ExtraMini)
                )
            }
        }

        // Invisible clickable overlay to capture clicks reliably across platforms
        if (enabled) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        showDialog = true
                    }
            )
        }
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                            val formattedDate = formatter.format(Date(selectedMillis))
                            onDateSelected(formattedDate)
                        }
                        showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
