package com.mamon.tablekmp.senario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mamon.tablekmp.ProjectData
import com.mamon.tablekmp.component.Table
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig
import com.mamon.tablekmp.sampleProjects
import org.jetbrains.compose.resources.painterResource
import tablekmp.composeapp.generated.resources.Res
import tablekmp.composeapp.generated.resources.ic_menu

@Composable
fun ComplexScenario2_TaskBoard() {
    var items by remember { mutableStateOf(sampleProjects) }

    val columns = listOf(
        TableColumn<ProjectData>(
            "",
            weight = 0.5f,
            minWidth = 50.dp,
            contentAlignment = Alignment.CenterHorizontally
        ) { _, _ ->
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(Res.drawable.ic_menu),
                contentDescription = null,
                tint = Color.Gray
            )
        },
        TableColumn<ProjectData>("Task", weight = 2.5f, minWidth = 180.dp) { it, _ ->
            Text(text = it.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        },
        TableColumn<ProjectData>("Priority", weight = 1.5f, minWidth = 100.dp) { it, _ ->
            val (bgColor, textColor) = when (it.priority) {
                "High" -> Color(0xFFFEE2E2) to Color(0xFF991B1B)
                "Medium" -> Color(0xFFFEF3C7) to Color(0xFF92400E)
                else -> Color(0xFFF3F4F6) to Color(0xFF374151)
            }
            Box(
                modifier = Modifier.background(bgColor, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(it.priority, color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        },
        TableColumn<ProjectData>("Status", weight = 1.5f, minWidth = 120.dp) { it, _ ->
            val color = when (it.status) {
                "Active" -> Color(0xFF10B981)
                "Failed" -> Color(0xFFEF4444)
                "Done" -> Color(0xFF3B82F6)
                else -> Color(0xFFF59E0B)
            }
            SuggestionChip(
                onClick = {},
                label = { Text(it.status, color = color, fontSize = 12.sp) },
                border = SuggestionChipDefaults.suggestionChipBorder(
                    enabled = true,
                    borderColor = color.copy(alpha = 0.5f)
                )
            )
        }
    )

    Table(
        items = items,
        columns = columns,
        onItemsReordered = { items = it },
        config = TableConfig(
            dragDropEnabled = true,
            rowSpacing = 10.dp,
            rowElevation = 3.dp,
            rowShape = RoundedCornerShape(12.dp),
            borderWidth = 0.dp,
            rowHeight = 65.dp,
            contentPadding = 4.dp,
            showHorizontalScrollTool = true
        ),
        colors = TableColors(
            headerContainerColor = Color.Transparent,
            trackColor = Color.Transparent,
            hoverColor = Color(0xFFF1F5F9)
        )
    )
}

@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
fun ComplexScenario2_TaskBoardPreview(){
    ComplexScenario2_TaskBoard()
}