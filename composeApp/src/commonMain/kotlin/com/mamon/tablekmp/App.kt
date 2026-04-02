package com.mamon.tablekmp


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview


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