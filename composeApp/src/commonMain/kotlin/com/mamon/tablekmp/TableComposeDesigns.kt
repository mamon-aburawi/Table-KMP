package com.mamon.tablekmp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mamon.tablekmp.senario.ComplexScenario1_EnterpriseGrid
import com.mamon.tablekmp.senario.ComplexScenario2_TaskBoard
import com.mamon.tablekmp.senario.ComplexScenario3_DarkAnalytics
import com.mamon.tablekmp.senario.ComplexScenario4_AdminDashboard
import com.mamon.tablekmp.senario.ComplexScenario5_AllUsersDirectory




@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TableShowcaseGallery() {
    val options = listOf(
        "1. Enterprise Financial Grid",
        "2. Interactive Task Board",
        "3. Dark Mode Analytics",
        "4. Modern Admin Dashboard",
        "5. Clean Users Directory"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    MaterialTheme {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isCompactScreen = maxWidth < 600.dp
            val horizontalPadding = if (isCompactScreen) 16.dp else 32.dp
            val topPadding = if (isCompactScreen) 16.dp else 24.dp

            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("TableKMP: Pro Showcase", fontWeight = FontWeight.Bold, fontSize = if (isCompactScreen) 18.sp else 22.sp) },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF8FAFC))
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(innerPadding)
                        .padding(horizontal = horizontalPadding, vertical = topPadding)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = options[selectedIndex],
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Design Scenario") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )

                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            options.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text) },
                                    onClick = {
                                        selectedIndex = index
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val description = when (selectedIndex) {
                        0 -> "Dense data, horizontal scrolling, sticky selection, striped rows, and rich avatars."
                        1 -> "Drag & Drop reordering enabled. Features row spacing, card elevations, and custom badges."
                        2 -> "Fully inverted theme, borderless rows, hover highlighting, and status indicators."
                        3 -> "Clean user management dashboard with dark headers, pill badges, and action icons."
                        else -> "Spacious rows, stacked text, custom pill coloring, and interactive action buttons." // <--- SCENARIO 5 DESCRIPTION
                    }

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = if (selectedIndex == 0 || selectedIndex == 3 || selectedIndex == 4) 1.dp else 0.dp,
                                color = Color(0xFFE2E8F0),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(if (selectedIndex == 1) Color(0xFFF8FAFC) else Color.Transparent)
                            .padding(if (selectedIndex == 1 && !isCompactScreen) 8.dp else 0.dp)
                    ) {
                        when (selectedIndex) {
                            0 -> ComplexScenario1_EnterpriseGrid()
                            1 -> ComplexScenario2_TaskBoard()
                            2 -> ComplexScenario3_DarkAnalytics()
                            3 -> ComplexScenario4_AdminDashboard()
                            4 -> ComplexScenario5_AllUsersDirectory() // <--- SCENARIO 5 CALLED HERE
                        }
                    }
                }
            }
        }
    }
}