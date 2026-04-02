package com.mamon.tablekmp.senario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mamon.tablekmp.ProjectData
import com.mamon.tablekmp.component.Table
import com.mamon.tablekmp.formatCurrency
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig
import com.mamon.tablekmp.sampleProjects

@Composable
fun ComplexScenario3_DarkAnalytics() {
    val columns :List<TableColumn<ProjectData>> = listOf(
        TableColumn("Project", weight = 1.5f) { item, isSelected ->
            Text(
                text = item.name,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            )
        },
        TableColumn("Status", weight = 1.0f) { item, isSelected ->
            val dotColor = when (item.status) {
                "Active" -> Color(0xFF34D399)
                "Done" -> Color(0xFF60A5FA)
                "Failed" -> Color(0xFFF87171)
                else -> Color(0xFFFBBF24)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(dotColor))
                Spacer(Modifier.width(8.dp))

                Text(
                    text = item.status,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
        },
        TableColumn(
            name = "Rev",
            weight = 1.2f,
            headerAlignment = Alignment.End,
            contentAlignment = Alignment.End
        ) { item, isSelected ->
            Text(
                text = formatCurrency(item.revenue),
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontFamily = FontFamily.Monospace
            )
        }
    )

    Table(
        items = sampleProjects,
        columns = columns,
        config = TableConfig(
            highlightClickedRow = true,
            borderWidth = 1.dp,
            showHorizontalScrollTool = true,
            hoverEnabled = false,
            rowHeight = 65.dp,
            dragDropEnabled = true,
        ),
        colors = TableColors(
            headerContainerColor = Color(0xFF0F172A),
            headerContentColor = Color(0xFF94A3B8),
            unSelectedRowContainerColor = Color(0xFF1E293B),
            selectedRowContainerColor = Color(0xFF94A3B8).copy(alpha = 0.2f),
            unSelectedRowContentColor = Color(0xFFD1D5DB),
            selectedRowContentColor = Color(0xFF10B981),
            rowBorderColor = Color(0xFF0F172A),
            trackColor = Color(0xFF0F172A)
        )
    )
}


@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
fun ComplexScenario3_DarkAnalyticsPreview(){
    ComplexScenario3_DarkAnalytics()
}