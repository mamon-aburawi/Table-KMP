package com.mamon.tablekmp.model

import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * @param stripedEnabled If true, applies alternating background colors to rows for better readability.
 * @param showHorizontalScrollTool If true, displays a custom draggable tool for horizontal scrolling when the table exceeds screen width.
 * @param showVerticalScrollTool If true, displays a custom draggable tool for vertical scrolling.
 * @param selectionEnabled If true, renders a leading column with checkboxes allowing users to select multiple rows.
 * @param dragDropEnabled If true, enables long-press drag-and-drop gestures for reordering rows.
 * @param hoverEnabled If true, applies a visual hover effect when a pointer (like a mouse) rests over a row.
 * @param highlightClickedRow If true, visually highlights a row indefinitely after it has been clicked.
 * @param rippleEffectEnabled If true, displays the standard Material design ripple animation when a row is clicked.
 * @param borderWidth The thickness of the borders separating rows and columns. Set to 0.dp to remove borders.
 * @param rowHeight The fixed height for standard data rows.
 * @param headerHeight The fixed height for the top header row.
 * @param rowShape The Compose [Shape] applied to data rows (e.g., RectangleShape, RoundedCornerShape). Useful when adding row spacing.
 * @param headerShape The Compose [Shape] applied to the header row.
 * @param rowSpacing The vertical gap distance between individual rows.
 * @param rowElevation The shadow elevation applied to data rows. Often used in combination with [rowSpacing].
 * @param contentPadding Outer padding applied around the entire table content (header and body).
 */


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

