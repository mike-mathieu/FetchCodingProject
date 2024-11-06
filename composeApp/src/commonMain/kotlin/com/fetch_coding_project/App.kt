package com.fetch_coding_project

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val mainViewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })
        val uiState by mainViewModel.uiState.collectAsState()
        LaunchedEffect(mainViewModel) {
            mainViewModel.updateItems()
        }

        Text(uiState.items.toString(), Modifier.verticalScroll(rememberScrollState()))
    }
}