package com.example.baltazar.core.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape

@Composable
fun WriteReviewDialog(
    onDismiss: () -> Unit,
    onSubmit: (rating: Int, comment: String) -> Unit,
    isSubmitting: Boolean = false
) {
    var inputRating by remember { mutableIntStateOf(5) }
    var inputComment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.write_review_dialog_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Spaces.Medium)) {
                Text(
                    text = stringResource(R.string.rate_service_label),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    (1..5).forEach { star ->
                        Icon(
                            imageVector = if (star <= inputRating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "$star star",
                            tint = if (star <= inputRating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(IconSizes.Huge)
                                .clickable { inputRating = star }
                                .padding(Paddings.ExtraMini)
                        )
                    }
                }

                OutlinedTextField(
                    value = inputComment,
                    onValueChange = { inputComment = it },
                    label = { Text(stringResource(R.string.your_thoughts_label)) },
                    placeholder = { Text(stringResource(R.string.share_experience_placeholder)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            RoundedButton(
                text = stringResource(R.string.send),
                onClick = {
                    onSubmit(inputRating, inputComment)
                    onDismiss()
                },
                isLoading = isSubmitting,
                shape = CornerShape.Rounded,
                borderRadius = BorderRadiuses.Medium
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
