package com.mamon.tablekmp.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import com.mamon.tablekmp.model.TableColumn

internal fun Modifier.tableColumnModifier(
    rowScope: RowScope,
    column: TableColumn<*>,
    requiresHorizontalScroll: Boolean
): Modifier {
    return this.then(
        if (requiresHorizontalScroll) {
            Modifier.width(column.minWidth)
        } else {
            if (column.weight > 0f) {
                with(rowScope) { Modifier.weight(column.weight) }
            } else {
                Modifier.width(column.minWidth)
            }
        }
    )
}