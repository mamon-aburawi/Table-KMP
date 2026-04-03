package com.mamon.tablekmp.component


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mamon.tablekmp.dragdrop.HorizontalDraggableTool
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig
import com.mamon.tablekmp.dragdrop.VerticalDraggableTool
import com.mamon.tablekmp.dragdrop.rememberDragDropState

/**
 * @param T The type of data represented by each row in the table.
 * @param items The list of data items to be displayed in the table.
 * @param columns The definitions for each column, including sizing and content rendering logic.
 * @param isLoading Determines whether the table should hide its content and display the loading state.
 * @param config The [TableConfig] used to toggle features (e.g., selection, dragging) and adjust sizing/shapes.
 * @param colors The [TableColors] defining the exact color palette for headers, rows, and tools.
 * @param onItemClicked Callback invoked when a user clicks on a table row. Provides the clicked item of type [T].
 * @param onSelectionChanged Callback invoked when row selection changes. Provides the updated list of selected items.
 * @param onItemsReordered Callback invoked after a user finishes dragging and dropping a row to a new position. Provides the newly ordered list.
 * @param onLoad A composable lambda to render a custom loading UI when [isLoading] is true.
 * @param onEmpty A composable lambda to render a custom empty state when [items] is empty and [isLoading] is false.
 * @param footerContent An optional composable lambda to render sticky or static content at the bottom of the table layout.
 */

@Composable
fun <T> Table(
    items: List<T>,
    columns: List<TableColumn<T>>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    config: TableConfig = TableConfig(),
    colors: TableColors = TableColors(),
    onItemClicked: (T) -> Unit = {},
    onSelectionChanged: (List<T>) -> Unit = {},
    onItemsReordered: (List<T>) -> Unit = {},
    onLoad: @Composable () -> Unit = {},
    onEmpty: @Composable () -> Unit = {},
    footerContent: @Composable (() -> Unit)? = null
) {
    val lazyListState = rememberLazyListState()
    val horizontalScrollState = rememberScrollState()

    val localItems = remember { mutableStateListOf<T>() }
    val selectedItems = remember { mutableStateListOf<T>() }
    var internalClickedItem by remember { mutableStateOf<T?>(null) }

    var lastSyncedItems by remember { mutableStateOf<List<T>>(emptyList()) }

    LaunchedEffect(items) {
        val newList = items.toList()
        if (newList != lastSyncedItems) {
            localItems.clear()
            localItems.addAll(newList)
            lastSyncedItems = newList
            if (!newList.contains(internalClickedItem)) internalClickedItem = null
        }
    }

    LaunchedEffect(selectedItems.size) { onSelectionChanged(selectedItems.toList()) }

    val dragDropState = rememberDragDropState(
        lazyListState = lazyListState,
        localItems = localItems,
        onItemsReordered = { newOrder ->
            lastSyncedItems = newOrder.toList()
            onItemsReordered(newOrder)
        }
    )

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val selectionColumnWidth = 50.dp
        val currentSelectionWidth by animateDpAsState(
            targetValue = if (config.selectionEnabled) selectionColumnWidth else 0.dp,
            label = "selectionWidth"
        )

        val totalMinWidth = columns.fold(0.dp) { acc, col -> acc + col.minWidth } +
                currentSelectionWidth + (config.contentPadding * 2)
        val requiresHorizontalScroll = totalMinWidth > maxWidth

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {

                val scrollModifier = if (requiresHorizontalScroll) {
                    Modifier.horizontalScroll(horizontalScrollState)
                } else Modifier

                Column(modifier = Modifier.fillMaxSize().then(scrollModifier)) {
                    val tableWidthModifier = if (requiresHorizontalScroll) Modifier.width(totalMinWidth) else Modifier.fillMaxWidth()

                    Column(modifier = tableWidthModifier) {
                        TableHeader(
                            columns = columns,
                            colors = colors,
                            config = config,
                            selectionColumnWidth = selectionColumnWidth,
                            requiresHorizontalScroll = requiresHorizontalScroll,
                            isAllSelected = localItems.isNotEmpty() && localItems.all { selectedItems.contains(it) },
                            onSelectAll = { isChecked ->
                                if (isChecked) selectedItems.addAll(localItems.filter { !selectedItems.contains(it) })
                                else selectedItems.removeAll(localItems)
                            }
                        )

                        if (!isLoading && localItems.isNotEmpty()) {
                            TableBody(
                                items = localItems,
                                lazyListState = lazyListState,
                                columns = columns,
                                colors = colors,
                                config = config,
                                dragDropState = dragDropState,
                                selectedItems = selectedItems,
                                internalClickedItem = internalClickedItem,
                                selectionColumnWidth = selectionColumnWidth,
                                requiresHorizontalScroll = requiresHorizontalScroll,
                                onRowChecked = { item, checked ->
                                    if (checked) item?.let { selectedItems.add(it) } else selectedItems.remove(item)
                                },
                                onClick = { clickedItem ->
                                    if (config.highlightClickedRow) internalClickedItem = clickedItem
                                    onItemClicked(clickedItem)
                                }
                            )
                        } else if (items.isEmpty() && !isLoading) {
                            TableEmptyState(onEmpty)
                        }
                    }
                }

                if (requiresHorizontalScroll && !isLoading && config.showHorizontalScrollTool) {
                    HorizontalDraggableTool(
                        trackColor = colors.trackColor,
                        scrollState = horizontalScrollState,
                        modifier = Modifier.align(Alignment.BottomCenter).padding(
                            bottom = 8.dp,
                            end = if (config.showVerticalScrollTool) 10.dp else 0.dp,
                            start = config.contentPadding
                        )
                    )
                }

                if (!isLoading && config.showVerticalScrollTool && localItems.isNotEmpty()) {
                    VerticalDraggableTool(
                        trackColor = colors.trackColor,
                        lazyListState = lazyListState,
                        modifier = Modifier.align(Alignment.CenterEnd).padding(
                            end = 8.dp,
                            top = config.contentPadding + 50.dp,
                            bottom = config.contentPadding + 20.dp
                        )
                    )
                }
            }
            if (footerContent != null) {
                Box(modifier = Modifier.fillMaxWidth().padding(config.contentPadding)) { footerContent() }
            }
        }
        if (isLoading) TableLoadState { onLoad() }
    }
}



@Composable
private fun TableLoadState(onLoad: @Composable () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { onLoad() }
}


@Composable
private fun ColumnScope.TableEmptyState(onEmpty: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        onEmpty()
    }
}










