package com.mamon.tablekmp.senario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.mamon.tablekmp.DirectoryUser
import com.mamon.tablekmp.component.Table
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import com.mamon.tablekmp.model.TableConfig
import com.mamon.tablekmp.sampleDirectory
import org.jetbrains.compose.resources.painterResource
import tablekmp.composeapp.generated.resources.Res
import tablekmp.composeapp.generated.resources.ic_delete
import tablekmp.composeapp.generated.resources.ic_menu

@Composable
fun ComplexScenario5_AllUsersDirectory() {
    var selectedItems by remember { mutableStateOf(emptyList<DirectoryUser>()) }

    val columns = listOf(
        TableColumn<DirectoryUser>("NAME", minWidth = 250.dp, weight = 2f) { user, _ ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Circular Initial Avatar
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFF1F5F9)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(user.name.take(1), fontWeight = FontWeight.Bold, color = Color(0xFF475569))
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        user.name,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E293B),
                        fontSize = 14.sp
                    )
                    Text(user.email, color = Color(0xFF94A3B8), fontSize = 13.sp)
                }
            }
        },
        TableColumn<DirectoryUser>("ROLE", minWidth = 140.dp, weight = 1f) { user, _ ->
            val (bgColor, textColor) = when (user.role) {
                "Super Admin" -> Color(0xFFF1F5F9) to Color(0xFF475569)
                "Admin" -> Color(0xFFEFF6FF) to Color(0xFF3B82F6)
                "Contributor" -> Color(0xFFECFDF5) to Color(0xFF10B981)
                else -> Color(0xFFF1F5F9) to Color(0xFF475569)
            }
            Box(
                modifier = Modifier.background(bgColor, RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    user.role,
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        TableColumn<DirectoryUser>("GROUPS", minWidth = 180.dp, weight = 1.5f) { user, _ ->
            Text(user.groups, color = Color(0xFF64748B), fontSize = 14.sp)
        },
        TableColumn<DirectoryUser>("STATUS", minWidth = 120.dp, weight = 1f) { user, _ ->
            val isColorActive = user.status == "Active"
            val statusColor = if (isColorActive) Color(0xFF22C55E) else Color(0xFFF43F5E)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(statusColor))
                Spacer(Modifier.width(8.dp))
                Text(
                    user.status,
                    color = statusColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        TableColumn<DirectoryUser>("ACTIONS", minWidth = 240.dp, weight = 1.5f) { _, isSelected ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset Password
                Row(
                    modifier = Modifier.clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_menu),
                        contentDescription = null,
                        tint = if (isSelected) Color(0xFF3B82F6) else Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "Reset Password",
                        color = if (isSelected) Color(0xFF3B82F6) else Color(0xFF94A3B8),
                        fontSize = 13.sp
                    )
                }
                // Delete
                Row(
                    modifier = Modifier.clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = null,
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("Delete", color = Color(0xFF94A3B8), fontSize = 13.sp)
                }
            }
        }
    )

    Table(
        items = sampleDirectory,
        columns = columns,
        onSelectionChanged = { selectedItems = it },
        config = TableConfig(
            stripedEnabled = false,
            selectionEnabled = true,
            showHorizontalScrollTool = true,
            dragDropEnabled = true,
            headerHeight = 48.dp,
            rowHeight = 76.dp, // Spacious rows like the image
            borderWidth = 1.dp,
        ),
        colors = TableColors(
            headerContainerColor = Color(0xFFF8FAFC),
            headerContentColor = Color(0xFF475569),
            unSelectedRowContainerColor = Color.White,
            selectedRowContainerColor = Color(0xFFF8FAFC), // Very subtle selection background
            rowBorderColor = Color(0xFFF1F5F9),
            hoverColor = Color(0xFFF8FAFC),
            headerCheckboxCheckedColor = Color(0xFF3B82F6),
            headerCheckboxUncheckedColor = Color(0xFFCBD5E1),
            rowCheckboxCheckedColor = Color(0xFF3B82F6),
            rowCheckboxUncheckedColor = Color(0xFFCBD5E1)
        )
    )
}



@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
fun ComplexScenario5_AllUsersDirectoryPreview(){
    ComplexScenario5_AllUsersDirectory()
}