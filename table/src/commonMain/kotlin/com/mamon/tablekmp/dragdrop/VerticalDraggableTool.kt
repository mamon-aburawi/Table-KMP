package com.mamon.tablekmp.dragdrop

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Suppress("FrequentlyChangingValue")
@Composable
internal fun VerticalDraggableTool(
    trackColor: Color,
    lazyListState: LazyListState,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val layoutInfo by remember { derivedStateOf { lazyListState.layoutInfo } }

    val totalItemsCount = layoutInfo.totalItemsCount
    val visibleItemsInfo = layoutInfo.visibleItemsInfo

    if (totalItemsCount == 0 || visibleItemsInfo.isEmpty()) return

    val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
    val firstVisibleItemScrollOffset = lazyListState.firstVisibleItemScrollOffset

    val averageItemSize = visibleItemsInfo.sumOf { it.size } / visibleItemsInfo.size.toFloat()
    val viewportHeightPx = layoutInfo.viewportSize.height.toFloat()
    val totalContentHeightPx = totalItemsCount * averageItemSize

    if (totalContentHeightPx <= viewportHeightPx) return

    BoxWithConstraints(
        modifier = modifier
            .fillMaxHeight()
            .width(10.dp)
            .background(trackColor, RoundedCornerShape(4.dp))
    ) {
        val trackHeightPx = with(density) { maxHeight.toPx() }

        val thumbHeightRatio = (viewportHeightPx / totalContentHeightPx).coerceIn(0f, 1f)
        val thumbHeightPx = trackHeightPx * thumbHeightRatio
        val thumbHeightDp = with(density) { thumbHeightPx.toDp() }.coerceAtLeast(40.dp)

        val estimatedPastScrollOffset = firstVisibleItemIndex * averageItemSize + firstVisibleItemScrollOffset
        val maxScrollOffset = (totalContentHeightPx - viewportHeightPx).coerceAtLeast(1f)
        val scrollFraction = (estimatedPastScrollOffset / maxScrollOffset).coerceIn(0f, 1f)

        val maxThumbOffsetPx = trackHeightPx - with(density) { thumbHeightDp.toPx() }
        val thumbOffsetPx = scrollFraction * maxThumbOffsetPx

        Box(
            modifier = Modifier
                .offset { IntOffset(0, thumbOffsetPx.toInt()) }
                .height(thumbHeightDp)
                .fillMaxWidth()
                .padding(horizontal = 1.dp)
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(4.dp)
                )
                .pointerInput(totalContentHeightPx, maxThumbOffsetPx) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            if (maxThumbOffsetPx > 0) {
                                val scrollRatio = maxScrollOffset / maxThumbOffsetPx
                                val scrollDelta = dragAmount.y * scrollRatio
                                lazyListState.scrollBy(scrollDelta)
                            }
                        }
                    }
                }
        )
    }
}

