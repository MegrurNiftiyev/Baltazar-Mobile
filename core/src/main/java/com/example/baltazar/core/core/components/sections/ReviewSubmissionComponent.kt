package com.example.baltazar.core.core.components.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape

@Composable
fun ReviewSubmissionComponent(
    onSubmit: (rating: Int, comment: String) -> Unit,
    modifier: Modifier = Modifier,
    isSubmitting: Boolean = false
) {
    var selectedRating by remember { mutableIntStateOf(5) }
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadiuses.Medium))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(Paddings.Medium),
        verticalArrangement = Arrangement.spacedBy(Spaces.Medium)
    ) {
        Text(
            text = stringResource(R.string.write_review_dialog_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spaces.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..5).forEach { star ->
                val isSelected = star <= selectedRating
                Icon(
                    imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "$star star",
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(IconSizes.Large)
                        .clickable { selectedRating = star }
                        .padding(Paddings.ExtraMini)
                )
            }
        }

        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.share_experience_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4,
            shape = RoundedCornerShape(BorderRadiuses.Small)
        )

        RoundedButton(
            text = stringResource(R.string.send),
            onClick = {
                if (commentText.isNotBlank()) {
                    onSubmit(selectedRating, commentText)
                    commentText = ""
                }
            },
            isLoading = isSubmitting,
            enabled = commentText.isNotBlank() && !isSubmitting,
            shape = CornerShape.Rounded,
            borderRadius = BorderRadiuses.Small
        )
    }
}
