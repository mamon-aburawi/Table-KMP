package com.mamon.tablekmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


data class User(
    val id: Int,
    val name: String
)

@Composable
fun App() {
    MaterialTheme {
        TableShowcaseGallery()
    }
}



@Composable
@Preview
fun AppPreview(){
    App()
}