package com.mamon.tablekmp.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.CompositionLocalProvider
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig
import com.mamon.tablekmp.utils.tableColumnModifier


@Composable
internal fun <T> TableRow(
    modifier: Modifier = Modifier,
    item: T?,
    index: Int,
    columns: List<TableColumn<T>>,
    colors: TableColors,
    config: TableConfig,
    isSelected: Boolean,
    isDragging: Boolean,
    draggingOffset: Float,
    isRowChecked: Boolean,
    selectionColumnWidth: Dp,
    requiresHorizontalScroll: Boolean,
    onRowChecked: (Boolean) -> Unit,
    onClick: (T) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val showHoverEffect = config.hoverEnabled && isHovered

    val baseColor = when {
        isDragging -> colors.rowContainerColor
        isSelected -> colors.selectedRowContainerColor
        config.stripedEnabled && index % 2 != 0 -> Color.LightGray.copy(alpha = 0.1f)
        else -> colors.rowContainerColor
    }

    val targetContentColor = if (isSelected) colors.selectedRowContentColor else colors.rowContentColor

    val animatedScale by animateFloatAsState(
        targetValue = if (isDragging) 1.04f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "scale"
    )

    val animatedElevation by animateDpAsState(
        targetValue = if (isDragging) 12.dp else config.rowElevation,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "elevation"
    )

    Card(
        shape = config.rowShape,
        border = if (config.borderWidth > 0.dp) BorderStroke(config.borderWidth, colors.rowBorderColor) else null,
        colors = CardDefaults.cardColors(containerColor = if (isDragging) baseColor else if (showHoverEffect) colors.hoverColor else baseColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(config.rowHeight)
            .zIndex(if (isDragging) 100f else 0f)
            .graphicsLayer {
                translationY = if (isDragging) draggingOffset else 0f
                scaleX = animatedScale
                scaleY = animatedScale
                shadowElevation = animatedElevation.toPx()
                shape = config.rowShape
                clip = true
            }
            .clickable(
                interactionSource = interactionSource,
                indication = if (config.rippleEffectEnabled) LocalIndication.current else null
            ) { item?.let { onClick(it) } }
            .then(if (config.hoverEnabled) Modifier.hoverable(interactionSource) else Modifier)
    ) {
        CompositionLocalProvider(LocalContentColor provides targetContentColor) {
            Row(modifier = Modifier.fillMaxSize().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                AnimatedVisibility(visible = config.selectionEnabled) {
                    Box(modifier = Modifier.width(selectionColumnWidth), contentAlignment = Alignment.Center) {
                        Checkbox(checked = isRowChecked, onCheckedChange = onRowChecked, colors = CheckboxDefaults.colors(checkedColor = colors.rowCheckboxCheckedColor, uncheckedColor = colors.rowCheckboxUncheckedColor, checkmarkColor = colors.rowCheckboxCheckmarkColor))
                    }
                }
                columns.forEach { column ->
                    Box(
                        modifier = Modifier
                            .tableColumnModifier(this, column, requiresHorizontalScroll)
                            .padding(horizontal = 16.dp),
                        contentAlignment = when (column.contentAlignment) {
                            Alignment.CenterHorizontally -> Alignment.Center
                            Alignment.End -> Alignment.CenterEnd
                            else -> Alignment.CenterStart
                        }
                    ) {
                        item?.let { column.content(it, isSelected) }
                    }
                }
            }
        }
    }
}

