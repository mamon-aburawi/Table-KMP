package com.mamon.tablekmp.dragdrop

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun HorizontalDraggableTool(
    trackColor: Color,
    scrollState: ScrollState,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(trackColor, RoundedCornerShape(4.dp))
    ) {
        val trackWidthPx = with(density) { maxWidth.toPx() }

        val totalWidthPx = trackWidthPx + scrollState.maxValue
        val thumbWidthRatio = (trackWidthPx / totalWidthPx).coerceIn(0f, 1f)
        val thumbWidthPx = trackWidthPx * thumbWidthRatio
        val thumbWidthDp = with(density) { thumbWidthPx.toDp() }.coerceAtLeast(40.dp)

        val maxThumbOffsetPx = trackWidthPx - with(density) { thumbWidthDp.toPx() }

        Box(
            modifier = Modifier
                .offset {
                    val scrollFraction = if (scrollState.maxValue > 0) {
                        scrollState.value.toFloat() / scrollState.maxValue
                    } else 0f
                    IntOffset((scrollFraction * maxThumbOffsetPx).toInt(), 0)
                }
                .width(thumbWidthDp)
                .fillMaxHeight()
                .padding(vertical = 1.dp)
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(4.dp)
                )
                .pointerInput(scrollState.maxValue, maxThumbOffsetPx) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            if (maxThumbOffsetPx > 0) {
                                val scrollRatio = scrollState.maxValue.toFloat() / maxThumbOffsetPx
                                val scrollDelta = dragAmount.x * scrollRatio
                                scrollState.scrollTo((scrollState.value + scrollDelta).toInt().coerceIn(0, scrollState.maxValue))
                            }
                        }
                    }
                }
        )
    }
}
