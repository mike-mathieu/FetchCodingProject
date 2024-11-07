package com.fetch_coding_project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
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

        ItemsPage(uiState)
    }
}

@Composable
fun ItemsPage(uiState: MainUiState) {
    Column {
        Row {

        }
        // TODO add loading / error
        AnimatedVisibility(visible = uiState.items.isNotEmpty()) {
            LazyColumn(contentPadding = PaddingValues(all = 8.dp)) {
                items(uiState.items) { item ->
                    ItemCell(item)
                }
            }
        }
    }
}

@Composable
fun ItemCell(item: FetchItem) {
    Text(item.toString())
}