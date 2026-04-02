package com.mamon.tablekmp.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig
import com.mamon.tablekmp.utils.tableColumnModifier

@Composable
internal fun <T> TableHeader(
    columns: List<TableColumn<T>>,
    colors: TableColors,
    config: TableConfig,
    selectionColumnWidth: Dp,
    requiresHorizontalScroll: Boolean,
    isAllSelected: Boolean,
    onSelectAll: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(config.headerHeight)
            .padding(start = config.contentPadding, end = config.contentPadding, top = config.contentPadding)
            .clip(config.headerShape)
            .background(colors.headerContainerColor)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = config.selectionEnabled, enter = expandHorizontally() + fadeIn(), exit = shrinkHorizontally() + fadeOut()) {
            Box(modifier = Modifier.width(selectionColumnWidth), contentAlignment = Alignment.Center) {
                Checkbox(checked = isAllSelected, onCheckedChange = onSelectAll, colors = CheckboxDefaults.colors(checkedColor = colors.headerCheckboxCheckedColor, uncheckedColor = colors.headerCheckboxUncheckedColor, checkmarkColor = colors.headerCheckboxCheckmarkColor))
            }
        }
        columns.forEach { column ->
            Box(
                modifier = Modifier.Companion
                    .tableColumnModifier(this, column, requiresHorizontalScroll)
                    .padding(horizontal = 16.dp),
                contentAlignment = when (column.headerAlignment) {
                    Alignment.CenterHorizontally -> Alignment.Center
                    Alignment.End -> Alignment.CenterEnd
                    else -> Alignment.CenterStart
                }
            ) {
                Text(
                    text = column.name,
                    fontWeight = FontWeight.Bold,
                    color = colors.headerContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


