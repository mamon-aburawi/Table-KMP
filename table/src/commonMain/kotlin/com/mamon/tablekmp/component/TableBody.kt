package com.mamon.tablekmp.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mamon.tablekmp.dragdrop.DragDropState
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig


@Composable
internal fun <T> TableBody(
    items: List<T>,
    lazyListState: LazyListState,
    columns: List<TableColumn<T>>,
    colors: TableColors,
    config: TableConfig,
    dragDropState: DragDropState<T>,
    selectedItems: List<T>,
    internalClickedItem: T?,
    selectionColumnWidth: Dp,
    requiresHorizontalScroll: Boolean,
    onRowChecked: (T?, Boolean) -> Unit,
    onClick: (T) -> Unit
) {
    val dragModifier = if (config.dragDropEnabled) dragDropState.pointerInputModifier else Modifier

    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(config.rowSpacing),
        modifier = Modifier.fillMaxHeight().fillMaxWidth().then(dragModifier),
        contentPadding = PaddingValues(top = config.rowSpacing, bottom = config.contentPadding + 60.dp, start = config.contentPadding, end = config.contentPadding)
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item?.hashCode() ?: 0 }
        ) { index, item ->

            TableRow(
                modifier = Modifier,

                item = item,
                index = index,
                columns = columns,
                colors = colors,
                config = config,
                isSelected = config.highlightClickedRow && internalClickedItem == item,
                isDragging = dragDropState.draggingIndex.value == index,
                draggingOffset = dragDropState.draggingOffset.value,
                isRowChecked = selectedItems.contains(item),
                selectionColumnWidth = selectionColumnWidth,
                requiresHorizontalScroll = requiresHorizontalScroll,
                onRowChecked = { checked -> onRowChecked(item, checked) },
                onClick = onClick
            )
        }
    }
}
