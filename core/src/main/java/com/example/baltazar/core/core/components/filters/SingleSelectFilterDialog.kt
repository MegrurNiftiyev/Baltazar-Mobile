package com.example.baltazar.core.core.components.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedButton

data class FilterOption<T>(
    val value: T,
    val label: String
)

@Composable
fun <T> SingleSelectFilterDialog(
    title: String,
    options: List<FilterOption<T>>,
    selectedOption: T?,
    onApply: (T?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tempSelected by remember { mutableStateOf(selectedOption) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(options) { option ->
                    val isSelected = tempSelected == option.value
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { tempSelected = option.value }
                            .padding(vertical = Paddings.ExtraSmall)
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { tempSelected = option.value },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = option.label,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = Spaces.ExtraSmall)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.ExtraSmall, vertical = Paddings.ExtraSmall),
                horizontalArrangement = Arrangement.spacedBy(Spaces.Small)
            ) {
                OutlinedButton(
                    onClick = {
                        tempSelected = null
                        onApply(null)
                    },
                    shape = RoundedCornerShape(BorderRadiuses.Medium),
                    modifier = Modifier
                        .weight(1f)
                        .height(Paddings.Massive)
                ) {
                    Text(
                        text = stringResource(id = R.string.reset_filters),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                RoundedButton(
                    onClick = { onApply(tempSelected) },
                    text = stringResource(id = R.string.apply_filters),
                    modifier = Modifier
                        .weight(1f)
                        .height(Paddings.Massive)
                )
            }
        },
        dismissButton = null,
        shape = RoundedCornerShape(BorderRadiuses.Large),
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = modifier
    )
}
