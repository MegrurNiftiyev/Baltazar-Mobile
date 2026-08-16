package com.example.baltazar.core.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IncludedServicesSection(
    services: List<String>,
    isLoading: Boolean = false,
    title: String? = null,
    modifier: Modifier = Modifier
) {
    if (services.isEmpty() && !isLoading) return

    Column(modifier = modifier.fillMaxWidth()) {
        if (!title.isNullOrBlank()) {
            SectionTitle(title = title)
            Spacer(modifier = Modifier.height(Spaces.Small))
        }

        if (isLoading) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spaces.Small)) {
                repeat(3) {
                    ShimmerWrapper(
                        isLoading = true,
                        modifier = Modifier
                            .width(Spaces.Max + Spaces.LargeMinus)
                            .height(Spaces.HugePlus)
                            .clip(CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Spaces.HugePlus)
                        )
                    }
                }
            }
        } else {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spaces.Small),
                verticalArrangement = Arrangement.spacedBy(Spaces.Small),
                modifier = Modifier.fillMaxWidth()
            ) {
                services.forEach { serviceName ->
                    Text(
                        text = serviceName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.5f))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                            .padding(horizontal = Paddings.Medium, vertical = Paddings.SmallMinus)
                    )
                }
            }
        }
    }
}
