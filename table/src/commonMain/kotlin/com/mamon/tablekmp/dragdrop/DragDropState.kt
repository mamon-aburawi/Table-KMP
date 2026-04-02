package com.mamon.tablekmp.dragdrop

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DragDropState<T>(
    private val lazyListState: LazyListState,
    private val localItems: MutableList<T>,
    private val onItemsReordered: (List<T>) -> Unit,
    private val scope: CoroutineScope
) {
    var draggingIndex = mutableStateOf<Int?>(null)
    private var dragY = mutableFloatStateOf(0f)
    private var initialItemTop = mutableFloatStateOf(0f)

    private var autoScrollJob: Job? = null

    val draggingOffset = derivedStateOf {
        val index = draggingIndex.value ?: return@derivedStateOf 0f
        val itemInfo = lazyListState.layoutInfo.visibleItemsInfo.find { it.index == index } ?: return@derivedStateOf 0f

        val targetTop = initialItemTop.floatValue + dragY.floatValue

        val vStart = lazyListState.layoutInfo.viewportStartOffset.toFloat()
        val vEnd = lazyListState.layoutInfo.viewportEndOffset.toFloat()
        val clampedTop = targetTop.coerceIn(vStart, vEnd - itemInfo.size)

        clampedTop - itemInfo.offset
    }

    val pointerInputModifier = Modifier.pointerInput(Unit) {
        detectDragGesturesAfterLongPress(
            onDragStart = { offset ->
                val itemInfo = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull {
                    offset.y.toInt() in it.offset..(it.offset + it.size)
                }
                if (itemInfo != null) {
                    draggingIndex.value = itemInfo.index
                    initialItemTop.floatValue = itemInfo.offset.toFloat()
                    dragY.floatValue = 0f
                }
            },
            onDrag = { change, dragAmount ->
                change.consume()
                dragY.floatValue += dragAmount.y

                checkSwap()
                checkAutoScroll()
            },
            onDragEnd = { endDrag() },
            onDragCancel = { endDrag() }
        )
    }

    private fun checkSwap() {
        val currentIndex = draggingIndex.value ?: return
        val currentItemInfo = lazyListState.layoutInfo.visibleItemsInfo.find { it.index == currentIndex } ?: return

        val targetTop = initialItemTop.floatValue + dragY.floatValue
        val vStart = lazyListState.layoutInfo.viewportStartOffset.toFloat()
        val vEnd = lazyListState.layoutInfo.viewportEndOffset.toFloat()
        val clampedTop = targetTop.coerceIn(vStart, vEnd - currentItemInfo.size)

        val draggedItemCenter = clampedTop + (currentItemInfo.size / 2f)

        val targetItem = lazyListState.layoutInfo.visibleItemsInfo.find {
            it.index != currentIndex &&
                    draggedItemCenter > it.offset &&
                    draggedItemCenter < (it.offset + it.size)
        }

        if (targetItem != null) {
            val targetIndex = targetItem.index


            val firstIndex = lazyListState.firstVisibleItemIndex
            val firstOffset = lazyListState.firstVisibleItemScrollOffset

            val item = localItems.removeAt(currentIndex)
            localItems.add(targetIndex, item)
            draggingIndex.value = targetIndex

            try {
                lazyListState.requestScrollToItem(firstIndex, firstOffset)
            } catch (e: Exception) {
                scope.launch { lazyListState.scrollToItem(firstIndex, firstOffset) }
            }
        }
    }

    private fun checkAutoScroll() {
        if (autoScrollJob?.isActive == true) return

        autoScrollJob = scope.launch {
            while (draggingIndex.value != null) {
                val currentIndex = draggingIndex.value ?: break
                val currentItemInfo = lazyListState.layoutInfo.visibleItemsInfo.find { it.index == currentIndex } ?: break

                val targetTop = initialItemTop.floatValue + dragY.floatValue
                val vStart = lazyListState.layoutInfo.viewportStartOffset.toFloat()
                val vEnd = lazyListState.layoutInfo.viewportEndOffset.toFloat()
                val clampedTop = targetTop.coerceIn(vStart, vEnd - currentItemInfo.size)

                val draggedItemCenter = clampedTop + (currentItemInfo.size / 2f)

                val scrollThreshold = (vEnd - vStart) * 0.15f
                val maxSpeed = 60f

                var speed = 0f
                if (draggedItemCenter < vStart + scrollThreshold) {
                    speed = -maxSpeed * (1f - (draggedItemCenter - vStart) / scrollThreshold).coerceIn(0f, 1f)
                } else if (draggedItemCenter > vEnd - scrollThreshold) {
                    speed = maxSpeed * (1f - (vEnd - draggedItemCenter) / scrollThreshold).coerceIn(0f, 1f)
                }

                if (speed != 0f) {
                    lazyListState.scrollBy(speed)
                    checkSwap()
                }

                delay(16)
            }
        }
    }

    private fun endDrag() {
        draggingIndex.value = null
        dragY.floatValue = 0f
        autoScrollJob?.cancel()
        autoScrollJob = null
        onItemsReordered(localItems.toList())
    }
}

@Composable
internal fun <T> rememberDragDropState(
    lazyListState: LazyListState,
    localItems: MutableList<T>,
    onItemsReordered: (List<T>) -> Unit
): DragDropState<T> {
    val scope = rememberCoroutineScope()
    return remember(lazyListState, localItems, onItemsReordered, scope) {
        DragDropState(lazyListState, localItems, onItemsReordered, scope)
    }
}

