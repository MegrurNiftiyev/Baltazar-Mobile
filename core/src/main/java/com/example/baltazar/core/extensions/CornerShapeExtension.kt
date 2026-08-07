package com.example.baltazar.core.extensions

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.example.baltazar.core.enums.CornerShape

fun CornerShape.toShape(radius: Dp): Shape = when (this) {
    CornerShape.Rounded -> RoundedCornerShape(radius)
    CornerShape.Circle -> CircleShape
    CornerShape.Rectangle -> RectangleShape
}