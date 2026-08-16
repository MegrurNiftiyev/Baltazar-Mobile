package com.example.baltazar.core.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import compose.icons.TablerIcons
import compose.icons.tablericons.InfoCircle

@Composable
fun ReviewIneligibleNotice(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadiuses.Medium))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.5f))
            .padding(Paddings.Medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = TablerIcons.InfoCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(IconSizes.MediumPlus)
        )
        Spacer(modifier = Modifier.width(Spaces.Small))
        Text(
            text = stringResource(R.string.reviews_ineligible_notice),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
