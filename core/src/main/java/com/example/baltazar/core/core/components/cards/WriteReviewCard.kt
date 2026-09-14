package com.example.baltazar.core.core.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape

@Composable
fun WriteReviewCard(
    onSubmit: (rating: Int, comment: String) -> Unit,
    modifier: Modifier = Modifier,
    isSubmitting: Boolean = false
) {
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadiuses.Large))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(BorderRadiuses.Large)
            )
            .padding(Paddings.Large)
    ) {
        Text(
            text = stringResource(R.string.rate_service_label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Spaces.Small))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..5).forEach { starIndex ->
                val isSelected = starIndex <= rating
                val interactionSource = remember { MutableInteractionSource() }
                Icon(
                    imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "$starIndex star",
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            rating = starIndex
                        }
                        .padding(end = Paddings.ExtraSmall)
                )
            }
        }

        Spacer(modifier = Modifier.height(Spaces.Medium))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.share_experience_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 110.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(BorderRadiuses.Medium),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            minLines = 3,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(Spaces.Medium))

        RoundedButton(
            text = stringResource(R.string.send),
            onClick = {
                if (!isSubmitting) {
                    onSubmit(rating, comment)
                }
            },
            isLoading = isSubmitting,
            shape = CornerShape.Rounded,
            borderRadius = BorderRadiuses.Medium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
