package com.example.baltazar.domain.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.baltazar.core.enums.HomeTab

data class BottomTabItem(
    val tab: HomeTab,
    val icon: ImageVector,
    @StringRes val label: Int
)
