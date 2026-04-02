package com.mamon.tablekmp.senario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
fun ComplexScenario1_EnterpriseGrid() {
    var selectedItems by remember { mutableStateOf(emptyList<ProjectData>()) }

    val columns = listOf(
        TableColumn<ProjectData>("ID", minWidth = 90.dp) { it, _ ->
            Text(it.id, style = MaterialTheme.typography.labelMedium)
        },
        TableColumn<ProjectData>("Project Details", minWidth = 240.dp) { it, _ ->
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    it.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(it.category, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        },
        TableColumn<ProjectData>("Team", minWidth = 140.dp) { it, _ ->
            Row(horizontalArrangement = Arrangement.spacedBy((-8).dp)) {
                it.teamMembers.take(3).forEach { initials ->
                    Box(
                        modifier = Modifier.size(28.dp).clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .border(1.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            initials,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (it.teamMembers.size > 3) {
                    Box(
                        modifier = Modifier.size(28.dp).clip(CircleShape)
                            .background(Color.LightGray).border(1.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "+${it.teamMembers.size - 3}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        TableColumn<ProjectData>("Progress", minWidth = 160.dp) { it, _ ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = { it.progress },
                    modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape),
                    color = if (it.progress == 1f) Color(0xFF10B981) else MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text("${(it.progress * 100).toInt()}%", fontSize = 12.sp, color = Color.Gray)
            }
        },
        TableColumn<ProjectData>(
            "Est. Revenue",
            minWidth = 150.dp,
            headerAlignment = Alignment.End,
            contentAlignment = Alignment.End
        ) { it, _ ->
            Text(
                formatCurrency(it.revenue),
                fontWeight = FontWeight.Medium,
                color = if (it.revenue > 100000) Color(0xFF059669) else Color.Unspecified
            )
        }
    )

    Table(
        items = sampleProjects,
        columns = columns,
        onSelectionChanged = { selectedItems = it },
        config = TableConfig(
            stripedEnabled = true,
            selectionEnabled = true,
            dragDropEnabled = true,
            showHorizontalScrollTool = true,
            rowHeight = 65.dp
        ),
        colors = TableColors(
            headerContainerColor = Color(0xFFF8FAFC),
            rowBorderColor = Color(0xFFE2E8F0)
        )
    )
}


@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
fun ComplexScenario1_EnterpriseGridPreview(){
    ComplexScenario1_EnterpriseGrid()
}