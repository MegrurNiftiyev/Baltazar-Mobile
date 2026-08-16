package com.example.baltazar.feature.food.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces

@Composable
fun NutritionChart(
    calories: Int,
    protein: Int,
    fat: Int,
    carb: Int,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (calories == 0 && protein == 0 && fat == 0 && carb == 0 && !isLoading) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BorderRadiuses.Medium))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(Paddings.Medium)
    ) {
        if (isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spaces.ExtraMini)
                    ) {
                        ShimmerWrapper(
                            isLoading = true,
                            modifier = Modifier
                                .size(IconSizes.ColossalMinus)
                                .clip(CircleShape)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(IconSizes.ColossalMinus)
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            )
                        }

                        ShimmerWrapper(
                            isLoading = true,
                            modifier = Modifier
                                .width(Spaces.GiantMinus)
                                .height(Spaces.Small)
                                .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(Spaces.GiantMinus)
                                    .height(Spaces.Small)
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            )
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MacroItem(
                    label = stringResource(R.string.calories_label),
                    value = "$calories",
                    unit = stringResource(R.string.kcal_suffix),
                    color = MaterialTheme.colorScheme.primary,
                    progress = (calories / 800f).coerceIn(0.1f, 1f)
                )
                MacroItem(
                    label = stringResource(R.string.protein_label),
                    value = "$protein",
                    unit = stringResource(R.string.gram_suffix),
                    color = MaterialTheme.colorScheme.secondary,
                    progress = (protein / 50f).coerceIn(0.1f, 1f)
                )
                MacroItem(
                    label = stringResource(R.string.fat_label),
                    value = "$fat",
                    unit = stringResource(R.string.gram_suffix),
                    color = MaterialTheme.colorScheme.tertiary,
                    progress = (fat / 50f).coerceIn(0.1f, 1f)
                )
                MacroItem(
                    label = stringResource(R.string.carb_label),
                    value = "$carb",
                    unit = stringResource(R.string.gram_suffix),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    progress = (carb / 100f).coerceIn(0.1f, 1f)
                )
            }
        }
    }
}

@Composable
private fun MacroItem(
    label: String,
    value: String,
    unit: String,
    color: Color,
    progress: Float
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(IconSizes.ColossalMinus),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(IconSizes.ColossalMinus),
                color = color.copy(alpha = 0.2f),
                strokeWidth = BorderRadiuses.Mini
            )
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(IconSizes.ColossalMinus),
                color = color,
                strokeWidth = BorderRadiuses.Mini,
                strokeCap = StrokeCap.Round
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(Spaces.ExtraMini))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = unit,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
