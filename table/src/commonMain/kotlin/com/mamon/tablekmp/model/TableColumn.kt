package com.mamon.tablekmp.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * @param T The type of data represented by the table rows.
 * @param name The title or label of the column, displayed in the table header.
 * @param minWidth The absolute minimum width this column is allowed to shrink to.
 * This is crucial for calculating when the table needs to switch to horizontal scrolling.
 * @param weight The flexible width proportion of this column relative to others.
 * Similar to Compose's `Modifier.weight()`, this determines how much of the available remaining space the column will occupy.
 * @param headerAlignment The horizontal alignment (Start, CenterHorizontally, End) of the text inside the column's header.
 * @param contentAlignment The horizontal alignment (Start, CenterHorizontally, End) of the items inside the column's body cells.
 * @param content A composable lambda that dictates exactly how the cell's UI should be rendered for this column.
 * It provides the current row [item] and a boolean [isSelected] indicating if the row is currently selected.
 */


data class TableColumn<T>(
    val name: String,
    val minWidth: Dp = 120.dp,
    val weight: Float = 1f,
    val headerAlignment: Alignment.Horizontal = Alignment.Start,
    val contentAlignment: Alignment.Horizontal = Alignment.Start,
    val content: @Composable (item: T, isSelected: Boolean) -> Unit
)


fun <T> TableColumn(
    name: String,
    minWidth: Dp = 120.dp,
    weight: Float = 1f,
    headerAlignment: Alignment.Horizontal = Alignment.Start,
    contentAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable (item: T) -> Unit
): TableColumn<T> {
    return TableColumn(
        name = name,
        minWidth = minWidth,
        weight = weight,
        headerAlignment = headerAlignment,
        contentAlignment = contentAlignment,
        content = { item, _ -> content(item) }
    )
}