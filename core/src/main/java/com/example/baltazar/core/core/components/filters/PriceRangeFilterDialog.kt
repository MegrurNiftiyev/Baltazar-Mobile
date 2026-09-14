package com.example.baltazar.core.core.components.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun PriceRangeFilterDialog(
    initialMinPrice: Double?,
    initialMaxPrice: Double?,
    onApply: (minPrice: Double?, maxPrice: Double?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var minPriceText by remember { mutableStateOf(initialMinPrice?.let { if (it % 1.0 == 0.0) it.toLong().toString() else it.toString() } ?: "") }
    var maxPriceText by remember { mutableStateOf(initialMaxPrice?.let { if (it % 1.0 == 0.0) it.toLong().toString() else it.toString() } ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.filter_price_range),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = minPriceText,
                        onValueChange = { minPriceText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text(text = stringResource(id = R.string.filter_min_price)) },
                        placeholder = { Text(text = "0") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(BorderRadiuses.Medium),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(Spaces.Medium))
                    OutlinedTextField(
                        value = maxPriceText,
                        onValueChange = { maxPriceText = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text(text = stringResource(id = R.string.filter_max_price)) },
                        placeholder = { Text(text = "1000") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(BorderRadiuses.Medium),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.ExtraSmall, vertical = Paddings.ExtraSmall),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Spaces.Small)
            ) {
                OutlinedButton(
                    onClick = {
                        minPriceText = ""
                        maxPriceText = ""
                        onApply(null, null)
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
                    onClick = {
                        val min = minPriceText.toDoubleOrNull()
                        val max = maxPriceText.toDoubleOrNull()
                        onApply(min, max)
                    },
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
