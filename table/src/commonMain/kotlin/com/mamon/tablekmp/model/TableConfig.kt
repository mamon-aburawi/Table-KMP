package com.mamon.tablekmp.model

import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



data class TableConfig (
    val stripedEnabled: Boolean = false,
    val showHorizontalScrollTool: Boolean = false,
    val showVerticalScrollTool: Boolean = false,
    val selectionEnabled: Boolean = false,
    val dragDropEnabled: Boolean = false,
    val hoverEnabled: Boolean = false,
    val highlightClickedRow: Boolean = false,
    val rippleEffectEnabled: Boolean = false,
    val borderWidth: Dp = 0.5.dp,
    val rowHeight: Dp = 60.dp,
    val headerHeight: Dp = 60.dp,
    val rowShape: Shape = RectangleShape,
    val headerShape: Shape = RectangleShape,
    val rowSpacing: Dp = 0.dp,
    val rowElevation: Dp = 0.dp,
    val contentPadding: Dp = 0.dp
)

