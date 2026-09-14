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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun IncludedServicesFilterDialog(
    availableServices: List<String>,
    selectedServices: List<String>,
    onApply: (List<String>) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tempSelected = remember { mutableStateListOf<String>().apply { addAll(selectedServices) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.filter_included_services),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(availableServices) { service ->
                    val isChecked = tempSelected.contains(service)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isChecked) {
                                    tempSelected.remove(service)
                                } else {
                                    tempSelected.add(service)
                                }
                            }
                            .padding(vertical = Paddings.ExtraMini)
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    tempSelected.add(service)
                                } else {
                                    tempSelected.remove(service)
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = service,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = Spaces.Small)
                        )
                    }
                }
            }
        },
        confirmButton = {
            RoundedButton(
                onClick = { onApply(tempSelected.toList()) },
                text = stringResource(id = R.string.apply_filters),
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    tempSelected.clear()
                    onApply(emptyList())
                }
            ) {
                Text(
                    text = stringResource(id = R.string.reset_filters),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        shape = RoundedCornerShape(BorderRadiuses.Large),
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = modifier
    )
}
