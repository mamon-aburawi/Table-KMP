package com.mamon.tablekmp.model

import androidx.compose.ui.graphics.Color

/**
 * Defines the complete color palette for the Table component.
 *
 * @param headerContainerColor The background color of the table header row.
 * @param headerContentColor The color of the text and icons within the table header.
 * @param selectedRowContainerColor The background color of a row when it is actively selected.
 * @param rowContainerColor The default background color of unselected rows.
 * @param selectedRowContentColor The color of the text and icons within a selected row.
 * @param rowContentColor The default color of the text and icons within unselected rows.
 * @param hoverColor The background color overlay applied when a pointer hovers over a row.
 * @param rowBorderColor The color of the borders separating rows or framing the table.
 * @param trackColor The color of the scrollbar track when scrolling is active.
 * @param rowShadowColor The color of the drop shadow applied to rows (e.g., when a row is elevated during drag-and-drop).
 * @param headerCheckboxCheckedColor The background color of the header's select-all checkbox when checked.
 * @param headerCheckboxUncheckedColor The border color of the header's select-all checkbox when unchecked.
 * @param headerCheckboxCheckmarkColor The color of the checkmark icon inside the header's checkbox.
 * @param rowCheckboxCheckedColor The background color of a row's individual checkbox when checked.
 * @param rowCheckboxUncheckedColor The border color of a row's individual checkbox when unchecked.
 * @param rowCheckboxCheckmarkColor The color of the checkmark icon inside a row's checkbox.
 */

data class TableColors(
    val headerContainerColor: Color = Color.White,
    val headerContentColor: Color = Color.Black,
    val selectedRowContainerColor: Color = Color.White,
    val rowContainerColor: Color = Color.White,
    val selectedRowContentColor: Color = Color.White,
    val rowContentColor: Color = Color.Black,
    val hoverColor: Color = Color.Transparent,
    val rowBorderColor: Color = Color(0xFFE2E8F0),
    val trackColor: Color = Color(0xFFF1F1F1),
    val rowShadowColor: Color = Color.Transparent,
    val headerCheckboxCheckedColor: Color = headerContentColor,
    val headerCheckboxUncheckedColor: Color = headerContentColor.copy(alpha = 0.6f),
    val headerCheckboxCheckmarkColor: Color = headerContainerColor,
    val rowCheckboxCheckedColor: Color = headerContainerColor,
    val rowCheckboxUncheckedColor: Color = headerContainerColor.copy(alpha = 0.6f),
    val rowCheckboxCheckmarkColor: Color = headerContentColor
)