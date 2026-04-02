package com.mamon.tablekmp.senario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mamon.tablekmp.AdminUserData
import com.mamon.tablekmp.component.Table
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig
import com.mamon.tablekmp.sampleUsers
import org.jetbrains.compose.resources.painterResource
import tablekmp.composeapp.generated.resources.Res
import tablekmp.composeapp.generated.resources.ic_delete
import tablekmp.composeapp.generated.resources.ic_edit
import tablekmp.composeapp.generated.resources.ic_person

@Composable
fun ComplexScenario4_AdminDashboard() {
    var selectedItems by remember { mutableStateOf(emptyList<AdminUserData>()) }

    val columns = listOf(
        TableColumn<AdminUserData>("Full Name", minWidth = 200.dp, weight = 2f) { user, _ ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(Color(0xFFE2E8F0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_person),
                        contentDescription = null,
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    user.fullName,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF334155),
                    fontSize = 13.sp
                )
            }
        },
        TableColumn<AdminUserData>(
            "Email",
            minWidth = 200.dp,
            weight = 2f
        ) { it, _ -> Text(it.email, color = Color(0xFF3B82F6), fontSize = 13.sp) },
        TableColumn<AdminUserData>(
            "Username",
            minWidth = 140.dp,
            weight = 1.5f
        ) { it, _ -> Text(it.username, color = Color(0xFF64748B), fontSize = 13.sp) },
        TableColumn<AdminUserData>("Status", minWidth = 120.dp, weight = 1.2f) { user, _ ->
            val bgColor = when (user.status) {
                "Active" -> Color(0xFF22C55E)
                "Inactive" -> Color(0xFF94A3B8)
                "Banned" -> Color(0xFFEF4444)
                "Pending" -> Color(0xFF0F172A)
                "Suspended" -> Color(0xFFF97316)
                else -> Color.Gray
            }
            Box(
                modifier = Modifier.background(bgColor, CircleShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    user.status,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        TableColumn<AdminUserData>("Role", minWidth = 100.dp, weight = 1f) { it, _ ->
            Text(
                it.role,
                color = Color(0xFF64748B),
                fontSize = 13.sp
            )
        },
        TableColumn<AdminUserData>("Joined Date", minWidth = 140.dp, weight = 1.5f) { it, _ ->
            Text(
                it.joinedDate,
                color = Color(0xFF64748B),
                fontSize = 13.sp
            )
        },
        TableColumn<AdminUserData>("Last Active", minWidth = 130.dp, weight = 1.5f) { it, _ ->
            Text(
                it.lastActive,
                color = Color(0xFF64748B),
                fontSize = 13.sp
            )
        },
        TableColumn<AdminUserData>(
            "Actions",
            minWidth = 100.dp,
            weight = 1f,
            headerAlignment = Alignment.CenterHorizontally,
            contentAlignment = Alignment.CenterHorizontally
        ) { _, _ ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_edit),
                        contentDescription = "Edit",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = "Delete",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    )

    Table(
        items = sampleUsers,
        columns = columns,
        onSelectionChanged = { selectedItems = it },
        config = TableConfig(
            stripedEnabled = false,
            selectionEnabled = true,
            showHorizontalScrollTool = true,
            headerHeight = 48.dp,
            rowHeight = 60.dp,
            borderWidth = 1.dp,
            dragDropEnabled = true,
        ),
        colors = TableColors(
            headerContainerColor = Color(0xFF2A3441),
            headerContentColor = Color.White,
            unSelectedRowContainerColor = Color.White,
            rowBorderColor = Color(0xFFF1F5F9),
            hoverColor = Color(0xFFF8FAFC),
            headerCheckboxCheckedColor = Color.White,
            headerCheckboxUncheckedColor = Color(0xFF94A3B8),
            headerCheckboxCheckmarkColor = Color(0xFF2A3441)
        )
    )
}

@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
fun ComplexScenario4_AdminDashboardPreview(){
    ComplexScenario4_AdminDashboard()
}