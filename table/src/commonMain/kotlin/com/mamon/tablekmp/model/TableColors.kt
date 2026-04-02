package com.mamon.tablekmp.model

import androidx.compose.ui.graphics.Color

data class TableColors (
    val headerContainerColor: Color = Color.White,
    val headerContentColor: Color = Color.Black,
    val selectedRowContainerColor: Color = Color.White,
    val unSelectedRowContainerColor: Color = Color.White,
    val selectedRowContentColor: Color = Color.White,
    val unSelectedRowContentColor: Color = Color.Black,
    val hoverColor: Color = Color.Transparent,
    val rowBorderColor: Color = Color(0xFFE2E8F0),
    val trackColor: Color = Color(0xFFF1F1F1),
    val rowShadowColor: Color = Color.Transparent,
    val headerCheckboxCheckedColor: Color = headerContentColor,
    val headerCheckboxUncheckedColor: Color = headerContentColor.copy(alpha = 0.6f),
    val headerCheckboxCheckmarkColor: Color = headerContainerColor,
    val rowCheckboxCheckedColor: Color = headerContainerColor,
    val rowCheckboxUncheckedColor: Color = headerContainerColor.copy(alpha = 0.6f),
    val rowCheckboxCheckmarkColor: Color = headerContentColor
)