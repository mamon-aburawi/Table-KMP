package com.mamon.tablekmp.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


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